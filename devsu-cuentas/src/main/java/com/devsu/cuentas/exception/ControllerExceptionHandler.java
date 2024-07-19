package com.devsu.cuentas.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.PAYMENT_REQUIRED;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({CuentaNotFoundException.class, MovimientoNotFoundException.class})
    public ResponseStatusException entityNotFoundException(RuntimeException e) {
        return new ResponseStatusException(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseStatusException saldoInsuficienteException(SaldoInsuficienteException e) {
        return new ResponseStatusException(PAYMENT_REQUIRED, e.getMessage());
    }
}
