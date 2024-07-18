package com.devsu.clientes.service;

import com.devsu.clientes.domain.Cliente;
import com.devsu.clientes.exception.ClienteNotFoundException;

public interface ClienteService {

    Cliente get(String clienteId) throws ClienteNotFoundException;

    Cliente create(Cliente cliente);

    Cliente update(String clienteId, Cliente cliente) throws ClienteNotFoundException;

    void delete(String clienteId) throws ClienteNotFoundException;
}
