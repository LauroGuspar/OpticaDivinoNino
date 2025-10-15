package com.MultiSoluciones.optica.service.Impl;

import com.MultiSoluciones.optica.model.Detalle;
import com.MultiSoluciones.optica.model.Producto;
import com.MultiSoluciones.optica.model.Venta;
import com.MultiSoluciones.optica.repository.DetalleRepository;
import com.MultiSoluciones.optica.repository.ProductoRepository;
import com.MultiSoluciones.optica.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DetalleServiceImpl {
    private final DetalleRepository detalleRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    public DetalleServiceImpl(DetalleRepository detalleRepository, ProductoRepository productoRepository, VentaRepository ventaRepository) {
        this.detalleRepository = detalleRepository;
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
    }

    @Transactional(readOnly = true)
    public List<Detalle> listarTodo() {
        return detalleRepository.findAllByEstadoNot(2);
    }

    @Transactional(readOnly = true)
    public Set<Detalle> listarPorIdVenta(Long idVenta) {
        return detalleRepository.findAllByVentaId(idVenta);
    }

    @Transactional(readOnly = true)
    public Optional<Detalle> buscarPorId(Long id) {
        return detalleRepository.findById(id);
    }

    @Transactional
    public Detalle guardar(Detalle detalle) {
        Producto producto = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrada"));
        Venta venta = ventaRepository.findById(detalle.getVenta().getId())
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        detalle.setProducto(producto);
        detalle.setVenta(venta);
        detalle.setPrecioUnitario(detalle.getProducto().getPrecio());
        detalle.setSubtotal(detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())));

        return detalleRepository.save(detalle);
    }

    @Transactional
    public void eliminar(Long id) throws IOException {
        Detalle detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));

        detalle.setEstado(2); // Borrado lógico
        detalleRepository.save(detalle);
    }

    @Transactional
    public boolean cambiarEstado(Long id) {
        Optional<Detalle> opt = detalleRepository.findById(id);
        if (opt.isEmpty() || opt.get().getEstado() == 2) {
            return false;
        }
        Detalle detalle = opt.get();
        detalle.setEstado(detalle.getEstado() == 1 ? 0 : 1);
        detalleRepository.save(detalle);
        return true;
    }

}
