package com.devsu.cuentas.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.UUID;

@Data
@Entity
public class Movimiento {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @ManyToOne(fetch = LAZY)
    private Cuenta cuenta;

    @Column
    private LocalDateTime fecha;

    @Column
    private TipoMovimiento tipoMovimiento;

    @Column
    private BigDecimal valor;

    @Column
    private BigDecimal saldo;
}
