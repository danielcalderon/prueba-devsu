package com.devsu.cuentas.repository;

import com.devsu.cuentas.domain.Movimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends CrudRepository<Movimiento, String> {

    @Query("SELECT m FROM Movimiento m " +
            "WHERE m.fecha > :desde AND m.fecha < :hasta AND m.cuenta.clienteId = :clienteId " +
            "ORDER BY m.fecha")
    List<Movimiento> findByFechaAndClienteId(LocalDateTime desde, LocalDateTime hasta, String clienteId);
}
