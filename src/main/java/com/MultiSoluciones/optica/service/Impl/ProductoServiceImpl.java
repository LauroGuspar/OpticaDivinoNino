package com.MultiSoluciones.optica.service.Impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.MultiSoluciones.optica.model.Categoria;
import com.MultiSoluciones.optica.model.Marca;
import com.MultiSoluciones.optica.model.Producto;
import com.MultiSoluciones.optica.model.Unidad;
import com.MultiSoluciones.optica.repository.CategoriaRepository;
import com.MultiSoluciones.optica.repository.MarcaRepository;
import com.MultiSoluciones.optica.repository.ProductoRepository;
import com.MultiSoluciones.optica.repository.UnidadRepository;
import com.MultiSoluciones.optica.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final UnidadRepository unidadRepository; 

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, MarcaRepository marcaRepository, UnidadRepository unidadRepository) {
    this.productoRepository = productoRepository;
    this.categoriaRepository = categoriaRepository;
    this.marcaRepository = marcaRepository;
    this.unidadRepository = unidadRepository;
}
    
    @Override
    @Transactional(readOnly = true)
    public List<Producto> listarTodosLosProductos() {
        return productoRepository.findAllByEstadoNot(2);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    @Transactional
    @SuppressWarnings("CallToPrintStackTrace")
    public Producto guardarProducto(Producto producto, MultipartFile imagenFile) throws IOException {
        Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Marca marca = marcaRepository.findById(producto.getMarca().getId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        Unidad unidad = unidadRepository.findById(producto.getUnidad().getId())
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada"));

        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setUnidad(unidad);
        if (producto.getId() == null) {
            producto.setFechaCreacion(LocalDate.now());
        }

        if (imagenFile != null && !imagenFile.isEmpty()) {
            if (producto.getId() != null) {
                productoRepository.findById(producto.getId()).ifPresent(p -> {
                    if (p.getImagen() != null && !p.getImagen().isEmpty()) {
                        try {
                            eliminarImagen(p.getImagen());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            String nombreImagen = guardarImagen(imagenFile, categoria.getNombre());
            producto.setImagen(nombreImagen);
        }

        return productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) throws IOException {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getImagen() != null) {
            eliminarImagen(producto.getImagen());
        }
        
        producto.setEstado(2); // Borrado lógico
        producto.setImagen(null);
        productoRepository.save(producto);
    }

    @Override
    @Transactional
    public boolean cambiarEstadoProducto(Long id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        if (productoOpt.isEmpty() || productoOpt.get().getEstado() == 2) {
            return false;
        }
        int nuevoEstado = productoOpt.get().getEstado() == 1 ? 0 : 1;
        productoRepository.actualizarEstado(id, nuevoEstado);
        return true;
    }

    private String guardarImagen(MultipartFile imagenFile, String categoriaNombre) throws IOException {
        // Sanitizar el nombre de la categoría para usarlo como nombre de carpeta
        String nombreCarpeta = categoriaNombre.replaceAll("[^a-zA-Z0-9.-]", "_");
        
        // Crear el directorio específico para la categoría si no existe
        Path directorioCategoria = Paths.get(uploadDir, nombreCarpeta);
        if (Files.notExists(directorioCategoria)) {
            Files.createDirectories(directorioCategoria);
        }

        // Generar un nombre de archivo único
        String nombreUnico = UUID.randomUUID().toString() + "_" + imagenFile.getOriginalFilename();
        Path rutaCompleta = directorioCategoria.resolve(nombreUnico);

        // Guardar el archivo en el disco
        Files.write(rutaCompleta, imagenFile.getBytes());

        // Devolver la ruta relativa (carpeta/archivo.jpg) para guardarla en la BD
        return Paths.get(nombreCarpeta, nombreUnico).toString();
    }

    private void eliminarImagen(String nombreImagen) throws IOException {
        if (nombreImagen == null || nombreImagen.isEmpty()) return;
        
        Path rutaImagen = Paths.get(uploadDir).resolve(nombreImagen);
        Files.deleteIfExists(rutaImagen);
    }
}