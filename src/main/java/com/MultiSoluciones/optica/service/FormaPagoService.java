package com.MultiSoluciones.optica.service;

import com.MultiSoluciones.optica.model.FormaPago;
import com.MultiSoluciones.optica.model.Marca;

import java.util.List;
import java.util.Optional;

public interface FormaPagoService {
    List<FormaPago> listarFormaPagosActivos();
    List<FormaPago> listarTodos();
    Optional<FormaPago> buscarFormaPagoPorId(Long id);
    FormaPago guardarFormaPago(FormaPago formaPago);
    void eliminarFormaPago(Long id);
    boolean cambiarEstadoFormaPago(Long id);
}
