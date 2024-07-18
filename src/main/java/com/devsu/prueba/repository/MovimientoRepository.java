package com.devsu.prueba.repository;

import com.devsu.prueba.cuentas.Movimiento;
import org.springframework.data.repository.CrudRepository;

public interface MovimientoRepository extends CrudRepository<Movimiento, String> {
}
