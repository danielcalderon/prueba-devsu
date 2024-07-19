package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.dto.ReporteDTO;
import com.devsu.cuentas.exception.MovimientoNotFoundException;

import java.time.LocalDate;

public interface MovimientoService {

    Movimiento get(String movimientoId) throws MovimientoNotFoundException;

    Movimiento create(Movimiento movimiento);

    ReporteDTO createReport(LocalDate desde, LocalDate hasta, String clienteId);
}
