package com.MultiSoluciones.optica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MultiSoluciones.optica.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    List<Perfil> findAllByEstadoNot(Integer estado);
    Optional<Perfil> findByNombreIgnoreCase(String nombre);
}