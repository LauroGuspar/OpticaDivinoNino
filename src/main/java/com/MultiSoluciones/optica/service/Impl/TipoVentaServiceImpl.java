package com.MultiSoluciones.optica.service.Impl;

import com.MultiSoluciones.optica.model.FormaPago;
import com.MultiSoluciones.optica.model.TipoVenta;
import com.MultiSoluciones.optica.repository.TipoVentaRepository;
import com.MultiSoluciones.optica.service.TipoVentaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoVentaServiceImpl implements TipoVentaService {
    private final TipoVentaRepository tipoVentaRepository;

    public TipoVentaServiceImpl(TipoVentaRepository tipoVentaRepository) {
        this.tipoVentaRepository = tipoVentaRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<TipoVenta> listarActivos() {
        return tipoVentaRepository.findAllByEstadoNot(1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoVenta> listarTodos() {
        return tipoVentaRepository.findAllByEstadoNot(2);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoVenta> buscarPorId(Long id) {
        return tipoVentaRepository.findById(id);
    }

    @Override
    @Transactional
    public TipoVenta guardar(TipoVenta tipoVenta) {
        if (tipoVenta.getId() == null) {
            Optional<TipoVenta> existenteOpt = tipoVentaRepository.findByNombre(tipoVenta.getNombre());
            if (existenteOpt.isPresent()) {
                TipoVenta existente = existenteOpt.get();
                if (existente.getEstado() == 2) {
                    existente.setEstado(1);
                    return tipoVentaRepository.save(existente);
                } else {
                    throw new IllegalArgumentException("Ya existe un tipo de venta con el nombre: " + tipoVenta.getNombre());
                }
            }
        }
        return tipoVentaRepository.save(tipoVenta);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        TipoVenta tipoVenta = tipoVentaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de venta encontrado con ID: " + id));
        tipoVenta.setEstado(2);
        tipoVentaRepository.save(tipoVenta);
    }

    @Override
    @Transactional
    public boolean cambiarEstado(Long id) {
        Optional<TipoVenta> tipoVentaOpt = tipoVentaRepository.findById(id);
        if (tipoVentaOpt.isEmpty()) {
            return false;
        }
        TipoVenta tipoVenta = tipoVentaOpt.get();
        if (tipoVenta.getEstado() == 1) {
            tipoVentaRepository.actualizarEstado(id, 0);
        } else if (tipoVenta.getEstado() == 0) {
            tipoVentaRepository.actualizarEstado(id, 1);
        }
        return true;
    }
}
