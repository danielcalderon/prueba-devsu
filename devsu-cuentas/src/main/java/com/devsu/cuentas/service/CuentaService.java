package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.exception.CuentaNotFoundException;

public interface CuentaService {

    Cuenta get(String cuentaId) throws CuentaNotFoundException;

    Cuenta create(Cuenta cuenta);

    Cuenta update(String cuentaId, Cuenta cuenta) throws CuentaNotFoundException;

    void delete(String cuentaId) throws CuentaNotFoundException;
}
