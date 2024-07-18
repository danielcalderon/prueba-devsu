package com.devsu.prueba.service.implementation;

import com.devsu.prueba.clientes.Cliente;
import com.devsu.prueba.dto.ClienteDTO;
import com.devsu.prueba.exception.ClienteNotFoundException;
import com.devsu.prueba.mapper.ClienteMapper;
import com.devsu.prueba.repository.ClienteRepository;
import com.devsu.prueba.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;

    @Override
    public Cliente get(String clienteId) {
        final Optional<Cliente> cliente = clienteRepository.findById(clienteId);

        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new ClienteNotFoundException();
        }
    }

    @Override
    public Cliente create(ClienteDTO clienteDTO) {
        return clienteRepository.save(clienteMapper.toEntity(clienteDTO));
    }

    @Override
    public Cliente update(String clienteId, ClienteDTO clienteDTO) {
        final Cliente cliente = get(clienteId);

        cliente.setNombres(clienteDTO.getNombres());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
        cliente.setIdentificacion(clienteDTO.getIdentificacion());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setContrasena(clienteDTO.getContrasena());
        cliente.setEstado(clienteDTO.getEstado());

        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(String clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}
