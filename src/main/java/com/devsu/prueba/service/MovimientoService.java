package com.devsu.prueba.service;

import com.devsu.prueba.cuentas.Movimiento;
import com.devsu.prueba.dto.MovimientoDTO;
import com.devsu.prueba.exception.MovimientoNotFoundException;

public interface MovimientoService {

    Movimiento get(String movimientoId) throws MovimientoNotFoundException;

    Movimiento create(MovimientoDTO movimientoDTO);

    Movimiento update(String movimientoId, MovimientoDTO movimientoDTO) throws MovimientoNotFoundException;

    void delete(String movimientoId) throws MovimientoNotFoundException;
}
