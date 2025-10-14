package com.MultiSoluciones.optica.repository;

import com.MultiSoluciones.optica.model.FormaPago;
import com.MultiSoluciones.optica.model.TipoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoVentaRepository extends JpaRepository<TipoVenta, Long> {
    List<TipoVenta> findAllByEstado(Integer estado);
    List<TipoVenta> findAllByEstadoNot(Integer estado);
    Optional<TipoVenta> findById(Long id);
    Optional<TipoVenta> findByNombre(String nombre);
    @Modifying
    @Query("UPDATE TipoVenta t SET t.estado = :nuevoEstado WHERE t.id = :id")
    void actualizarEstado(Long id, Integer nuevoEstado);
}
