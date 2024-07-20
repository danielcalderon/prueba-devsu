package com.devsu.clientes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class ItemReporte {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String clienteId;

    @Column
    private LocalDateTime fecha;

    @Column
    private String clienteNombres;

    @Column
    private String numeroCuenta;

    @Column
    private String tipoCuenta;

    @Column
    private String saldoInicial;

    @Column
    private String estado;

    @Column
    private String movimiento;

    @Column
    private String saldoDisponible;
}
