package com.devsu.prueba.service;

import com.devsu.prueba.cuentas.Movimiento;
import com.devsu.prueba.exception.MovimientoNotFoundException;

public interface MovimientoService {

    Movimiento get(String movimientoId) throws MovimientoNotFoundException;

    Movimiento create(Movimiento movimiento);

    Movimiento update(String movimientoId, Movimiento movimiento) throws MovimientoNotFoundException;

    void delete(String movimientoId) throws MovimientoNotFoundException;
}
