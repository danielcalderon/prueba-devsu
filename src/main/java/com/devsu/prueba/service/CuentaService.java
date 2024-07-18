package com.devsu.prueba.service;

import com.devsu.prueba.cuentas.Cuenta;
import com.devsu.prueba.exception.CuentaNotFoundException;

public interface CuentaService {

    Cuenta get(String cuentaId) throws CuentaNotFoundException;

    Cuenta create(Cuenta cuenta);

    Cuenta update(String cuentaId, Cuenta cuenta) throws CuentaNotFoundException;

    void delete(String cuentaId) throws CuentaNotFoundException;
}
