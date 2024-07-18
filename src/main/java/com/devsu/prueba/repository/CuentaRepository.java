package com.devsu.prueba.repository;

import com.devsu.prueba.cuentas.Cuenta;
import org.springframework.data.repository.CrudRepository;

public interface CuentaRepository extends CrudRepository<Cuenta, String> {
}
