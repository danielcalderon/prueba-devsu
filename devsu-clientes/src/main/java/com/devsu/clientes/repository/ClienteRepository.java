package com.devsu.clientes.repository;

import com.devsu.clientes.domain.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, String> {
}
