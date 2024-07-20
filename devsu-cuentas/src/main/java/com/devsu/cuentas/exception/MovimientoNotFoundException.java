package com.devsu.cuentas.exception;

public class MovimientoNotFoundException extends RuntimeException {

    public MovimientoNotFoundException() {
        super("Movimiento inexistente");
    }
}
