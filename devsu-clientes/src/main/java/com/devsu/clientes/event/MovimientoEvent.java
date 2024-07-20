package com.devsu.clientes.event;

import lombok.Value;

import java.io.Serializable;

@Value
public class MovimientoEvent implements Serializable {
    String fecha;
    String clienteId;
    String numeroCuenta;
    String tipoCuenta;
    String saldoInicial;
    String estado;
    String movimiento;
    String saldoDisponible;
}
