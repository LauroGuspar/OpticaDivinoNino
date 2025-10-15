package com.MultiSoluciones.optica.repository;

import com.MultiSoluciones.optica.model.Producto;
import com.MultiSoluciones.optica.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findAllByEstadoNot(Integer estado);
    List<Venta> findAllByFechaAfterAndFechaBefore(LocalDateTime fechaInicial, LocalDateTime fechaFinal);
    List<Venta> findAllBySituacion(String situacion);
}
