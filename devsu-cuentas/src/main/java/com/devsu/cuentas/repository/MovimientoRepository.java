package com.devsu.cuentas.repository;

import com.devsu.cuentas.domain.Movimiento;
import org.springframework.data.repository.CrudRepository;

public interface MovimientoRepository extends CrudRepository<Movimiento, String> {
}
