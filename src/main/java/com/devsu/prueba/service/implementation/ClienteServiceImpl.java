package com.devsu.prueba.service.implementation;

import com.devsu.prueba.clientes.Cliente;
import com.devsu.prueba.exception.ClienteNotFoundException;
import com.devsu.prueba.repository.ClienteRepository;
import com.devsu.prueba.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente get(String clienteId) {
        return clienteRepository.findById(clienteId).orElseThrow(ClienteNotFoundException::new);
    }

    @Override
    public Cliente create(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(String clienteId, Cliente updateCliente) {
        final Cliente cliente = get(clienteId);

        cliente.setNombres(updateCliente.getNombres());
        cliente.setGenero(updateCliente.getGenero());
        cliente.setFechaNacimiento(updateCliente.getFechaNacimiento());
        cliente.setIdentificacion(updateCliente.getIdentificacion());
        cliente.setDireccion(updateCliente.getDireccion());
        cliente.setTelefono(updateCliente.getTelefono());
        cliente.setContrasena(updateCliente.getContrasena());
        cliente.setEstado(updateCliente.getEstado());

        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(String clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}
