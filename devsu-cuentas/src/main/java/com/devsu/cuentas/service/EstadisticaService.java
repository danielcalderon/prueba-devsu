package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.domain.Movimiento;

public interface EstadisticaService {

    void movimientoCreado(Movimiento movimiento, Cuenta cuenta);
}
