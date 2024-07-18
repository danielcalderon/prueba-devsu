package com.devsu.prueba.service;

import com.devsu.prueba.cuentas.Cuenta;
import com.devsu.prueba.dto.CuentaDTO;
import com.devsu.prueba.exception.CuentaNotFoundException;

public interface CuentaService {

    Cuenta get(String cuentaId) throws CuentaNotFoundException;

    Cuenta create(CuentaDTO cuentaDTO);

    Cuenta update(String cuentaId, CuentaDTO cuentaDTO) throws CuentaNotFoundException;

    void delete(String cuentaId) throws CuentaNotFoundException;
}
