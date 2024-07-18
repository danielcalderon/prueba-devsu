package com.devsu.prueba.cuentas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
public class Movimiento {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column
    private LocalDateTime fecha;

    @Column
    private String tipoMovimiento;

    @Column
    private BigDecimal valor;

    @Column
    private BigDecimal saldo;
}
