package com.MultiSoluciones.optica.repository;

import com.MultiSoluciones.optica.model.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DetalleRepository extends JpaRepository<Detalle, Long> {
    List<Detalle> findAllByEstadoNot(Integer estado);
    Set<Detalle> findAllByVentaId(Long idVenta);

}
