package com.devsu.prueba.mapper;

import com.devsu.prueba.clientes.Cliente;
import com.devsu.prueba.dto.ClienteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO clienteDTO);

    ClienteDTO toDTO(Cliente cliente);
}
