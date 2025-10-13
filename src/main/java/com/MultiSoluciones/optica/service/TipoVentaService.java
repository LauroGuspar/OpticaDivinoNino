package com.MultiSoluciones.optica.service;

import com.MultiSoluciones.optica.model.FormaPago;
import com.MultiSoluciones.optica.model.TipoVenta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TipoVentaService {
    List<TipoVenta> listarActivos();
    Optional<FormaPago> buscarPorId(Long id);
    FormaPago guardar(FormaPago formaPago);
    void eliminar(Long id);
    boolean cambiarEstado(Long id);
}
