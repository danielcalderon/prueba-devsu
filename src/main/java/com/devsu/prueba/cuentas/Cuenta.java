package com.devsu.prueba.cuentas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
public class Cuenta {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String numeroCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private boolean estado;
}
