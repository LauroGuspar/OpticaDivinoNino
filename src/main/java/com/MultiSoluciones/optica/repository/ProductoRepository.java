package com.MultiSoluciones.optica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.MultiSoluciones.optica.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findAllByEstadoNot(int estado);

    @Modifying
    @Query("UPDATE Producto p SET p.estado = :nuevoEstado WHERE p.id = :id")
    void actualizarEstado(Long id, int nuevoEstado);
}