package com.devsu.cuentas.domain;

import jakarta.persistence.Column;
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

    @Column
    private String numeroCuenta;

    @Column
    private String tipoCuenta;

    @Column
    private BigDecimal saldo;

    @Column
    private Boolean estado;
}
