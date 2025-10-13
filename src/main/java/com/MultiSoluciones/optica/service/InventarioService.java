package com.MultiSoluciones.optica.service;

import com.MultiSoluciones.optica.model.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioService {
    List<Inventario> listarInventarios();
    Optional<Inventario> obtenerInventarioPorId(Long id);
    Inventario guardarInventario(Inventario inventario);
}
