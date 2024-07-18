package com.devsu.prueba.repository;

import com.devsu.prueba.clientes.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, String> {
}
