package com.MultiSoluciones.optica.service.Impl;

import com.MultiSoluciones.optica.model.Opcion;
import com.MultiSoluciones.optica.model.TipoVenta;
import com.MultiSoluciones.optica.repository.OpcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OpcionServiceImpl {
    private final OpcionRepository opcionRepository;

    public OpcionServiceImpl(OpcionRepository opcionRepository) {
        this.opcionRepository = opcionRepository;
    }

    @Transactional(readOnly = true)
    public List<Opcion> listarOpciones() {
        return opcionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Opcion> buscarOpcionPorId(Long id) {
        return opcionRepository.findById(id);
    }

    @Transactional
    public Opcion guardar(Opcion opcion) {
        if (opcion.getId() == null) {
            Optional<Opcion> existenteOpt = opcionRepository.findByNombre(opcion.getNombre());
            if (existenteOpt.isPresent()) {
                Opcion existente = existenteOpt.get();
                return opcionRepository.save(existente);
            }
        }
        return opcionRepository.save(opcion);
    }
}
