package com.devsu.cuentas.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException() {
        super("Saldo no disponible");
    }
}
