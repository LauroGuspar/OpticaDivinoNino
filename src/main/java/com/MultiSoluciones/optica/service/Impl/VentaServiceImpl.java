package com.MultiSoluciones.optica.service.Impl;

import com.MultiSoluciones.optica.model.*;
import com.MultiSoluciones.optica.repository.DetalleRepository;
import com.MultiSoluciones.optica.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VentaServiceImpl {
    private final VentaRepository ventaRepository;
    private final DetalleRepository detalleRepository;
    
    public VentaServiceImpl(VentaRepository ventaRepository, DetalleRepository detalleRepository) {
        this.ventaRepository = ventaRepository;
        this.detalleRepository = detalleRepository;
    }

    @Transactional(readOnly = true)
    public List<Venta> listar() {
        return ventaRepository.findAllByEstadoNot(2);
    }
    
    @Transactional(readOnly = true)
    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    @Transactional
    @SuppressWarnings("CallToPrintStackTrace")
    public Venta guardar(Venta venta) throws IOException {
        Set<Detalle> detalles = detalleRepository.findAllByVentaId(venta.getId());

        venta.setDetalles(detalles);

        if (venta.getId() == null) {
            venta.setFecha(LocalDateTime.now());
        }

        return ventaRepository.save(venta);
    }

    
    @Transactional
    public void eliminar(Long id) throws IOException {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        venta.setEstado(2); // Borrado lógico
        ventaRepository.save(venta);
    }

    
    @Transactional
    public boolean cambiarEstado(Long id) {
        Optional<Venta> ventaOpt = ventaRepository.findById(id);
        if (ventaOpt.isEmpty() || ventaOpt.get().getEstado() == 2) {
            return false;
        }
        Venta venta = ventaOpt.get();
        venta.setEstado(venta.getEstado() == 1 ? 0 : 1);
        ventaRepository.save(venta);
        return true;
    }
    
}
