package com.devsu.clientes.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseStatusException clienteNotFoundException(ClienteNotFoundException e) {
        return new ResponseStatusException(NOT_FOUND, e.getMessage());
    }
}
