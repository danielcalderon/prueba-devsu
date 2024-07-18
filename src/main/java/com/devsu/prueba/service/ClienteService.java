package com.devsu.prueba.service;

import com.devsu.prueba.clientes.Cliente;
import com.devsu.prueba.dto.ClienteDTO;
import com.devsu.prueba.exception.ClienteNotFoundException;

public interface ClienteService {

    Cliente get(String clienteId) throws ClienteNotFoundException;

    Cliente create(ClienteDTO clienteDTO);

    Cliente update(String clienteId, ClienteDTO clienteDTO) throws ClienteNotFoundException;

    void delete(String clienteId) throws ClienteNotFoundException;
}
