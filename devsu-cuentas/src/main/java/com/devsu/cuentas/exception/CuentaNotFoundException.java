package com.devsu.cuentas.exception;

public class CuentaNotFoundException extends RuntimeException {

    public CuentaNotFoundException() {
        super("Cuenta inexistente");
    }
}
