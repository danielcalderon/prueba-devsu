package com.devsu.clientes.exception;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException() {
        super("Cliente inexistente");
    }
}
