package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.exception.MovimientoNotFoundException;

public interface MovimientoService {

    Movimiento get(String movimientoId) throws MovimientoNotFoundException;

    Movimiento create(Movimiento movimiento);

    Movimiento update(String movimientoId, Movimiento movimiento) throws MovimientoNotFoundException;

    void delete(String movimientoId) throws MovimientoNotFoundException;
}
