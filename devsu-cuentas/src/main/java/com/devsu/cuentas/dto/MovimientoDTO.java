package com.devsu.cuentas.dto;

import com.devsu.cuentas.domain.TipoMovimiento;
import com.devsu.cuentas.validator.EnumValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class MovimientoDTO {

    String id;

    @NotNull
    String cuentaId;

    @NotNull
    LocalDateTime fecha;

    String tipoMovimiento;

    @NotNull
    BigDecimal valor;

    BigDecimal saldoInicial;

    BigDecimal saldoFinal;
}
