package com.MultiSoluciones.optica.service.Impl;

import com.MultiSoluciones.optica.model.FormaPago;
import com.MultiSoluciones.optica.model.Marca;
import com.MultiSoluciones.optica.repository.FormaPagoRepository;
import com.MultiSoluciones.optica.service.FormaPagoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FormaPagoServiceImpl implements FormaPagoService {

    private final FormaPagoRepository formaPagoRepository;

    public FormaPagoServiceImpl(FormaPagoRepository formaPagoRepository) {
        this.formaPagoRepository = formaPagoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormaPago> listarFormaPagosActivos() {
        return formaPagoRepository.findAllByEstado(1);
    }

    @Override
    public Optional<FormaPago> buscarFormaPagoPorId(Long id) {
        return formaPagoRepository.findById(id);
    }

    @Override
    public FormaPago guardarFormaPago(FormaPago formaPago) {
        if (formaPago.getId() == null) {
            Optional<FormaPago> existenteOpt = formaPagoRepository.findByNombre(formaPago.getNombre());
            if (existenteOpt.isPresent()) {
                FormaPago existente = existenteOpt.get();
                if (existente.getEstado() == 2) {
                    existente.setEstado(1);
                    return formaPagoRepository.save(existente);
                } else {
                    throw new IllegalArgumentException("Ya existe una forma de pago con el nombre: " + formaPago.getNombre());
                }
            }
        }
        return formaPagoRepository.save(formaPago);
    }

    @Override
    public void eliminarFormaPago(Long id) {
        FormaPago formaPago = formaPagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Forma de pago no encontrada con ID: " + id));
        formaPago.setEstado(2);
        formaPagoRepository.save(formaPago);
    }

    @Override
    public boolean cambiarEstadoFormaPago(Long id) {
        Optional<FormaPago> formaOpt = formaPagoRepository.findById(id);
        if (formaOpt.isEmpty()) {
            return false;
        }
        FormaPago formaPago = formaOpt.get();
        if (formaPago.getEstado() == 1) {
            formaPagoRepository.actualizarEstado(id, 0);
        } else if (formaPago.getEstado() == 0) {
            formaPagoRepository.actualizarEstado(id, 1);
        }
        return true;
    }
}
