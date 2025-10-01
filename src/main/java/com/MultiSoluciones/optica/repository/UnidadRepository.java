package com.MultiSoluciones.optica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MultiSoluciones.optica.model.Unidad;

@Repository
public interface UnidadRepository extends JpaRepository<Unidad, Integer> {
    List<Unidad> findByEstado(int estado);
}