package com.devsu.prueba.service;

import com.devsu.prueba.clientes.Cliente;
import com.devsu.prueba.exception.ClienteNotFoundException;

public interface ClienteService {

    Cliente get(String clienteId) throws ClienteNotFoundException;

    Cliente create(Cliente cliente);

    Cliente update(String clienteId, Cliente cliente) throws ClienteNotFoundException;

    void delete(String clienteId) throws ClienteNotFoundException;
}
