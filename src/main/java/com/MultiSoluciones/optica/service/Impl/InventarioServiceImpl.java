package com.MultiSoluciones.optica.service.Impl;

import com.MultiSoluciones.optica.model.Inventario;
import com.MultiSoluciones.optica.model.Producto;
import com.MultiSoluciones.optica.repository.InventarioRepository;
import com.MultiSoluciones.optica.repository.ProductoRepository;
import com.MultiSoluciones.optica.service.InventarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioServiceImpl implements InventarioService {
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository, ProductoRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Inventario> listarInventarios() {
        return inventarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Inventario> obtenerInventarioPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Inventario guardarInventario(Inventario inventario) {
        Producto producto = productoRepository.findById(inventario.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrada"));

        inventario.setProducto(producto);

        return inventarioRepository.save(inventario);
    }
}
