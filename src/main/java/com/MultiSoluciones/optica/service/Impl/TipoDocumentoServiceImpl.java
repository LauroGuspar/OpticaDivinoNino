package com.MultiSoluciones.optica.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MultiSoluciones.optica.model.TipoDocumento;
import com.MultiSoluciones.optica.repository.TipoDocumentoRepository;
import com.MultiSoluciones.optica.service.TipoDocumentoService;

@Service
public class TipoDocumentoServiceImpl implements TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    public TipoDocumentoServiceImpl(TipoDocumentoRepository tipoDocumentoRepository) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoDocumento> listarTiposDocumentoActivos() {
        return tipoDocumentoRepository.findAllByEstado(1);
    }
}