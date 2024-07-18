package com.devsu.cuentas.repository;

import com.devsu.cuentas.domain.Cuenta;
import org.springframework.data.repository.CrudRepository;

public interface CuentaRepository extends CrudRepository<Cuenta, String> {
}
