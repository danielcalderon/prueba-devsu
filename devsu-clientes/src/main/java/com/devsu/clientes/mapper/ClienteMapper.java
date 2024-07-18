package com.devsu.clientes.mapper;

import com.devsu.clientes.domain.Cliente;
import com.devsu.clientes.dto.ClienteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO clienteDTO);

    ClienteDTO toDTO(Cliente cliente);
}
