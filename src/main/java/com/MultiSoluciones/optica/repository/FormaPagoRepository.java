package com.MultiSoluciones.optica.repository;

import com.MultiSoluciones.optica.model.FormaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormaPagoRepository extends JpaRepository<FormaPago, Long> {
    List<FormaPago> findAllByEstado(Integer estado);
    List<FormaPago> findAllByEstadoNot(Integer estado);
    Optional<FormaPago> findById(Long id);
    Optional<FormaPago> findByNombre(String nombre);
    @Modifying
    @Query("UPDATE FormaPago f SET f.estado = :nuevoEstado WHERE f.id = :id")
    void actualizarEstado(Long id, Integer nuevoEstado);
}
