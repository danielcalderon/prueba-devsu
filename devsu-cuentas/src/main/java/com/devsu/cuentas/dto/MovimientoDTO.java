package com.devsu.cuentas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class MovimientoDTO {

    String id;

    @NotNull
    LocalDateTime fecha;

    @NotBlank
    String tipoMovimiento;

    @NotNull
    BigDecimal valor;

    @NotNull
    BigDecimal saldo;
}
