package com.devsu.cuentas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class CuentaDTO {

    String id;

    @NotNull
    String clienteId;

    @NotBlank
    String numeroCuenta;

    @NotBlank
    String tipoCuenta;

    @Min(0)
    BigDecimal saldo;

    @NotNull
    Boolean estado;
}
