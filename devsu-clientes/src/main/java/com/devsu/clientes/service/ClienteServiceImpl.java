package com.devsu.clientes.service;

import com.devsu.clientes.domain.Cliente;
import com.devsu.clientes.exception.ClienteNotFoundException;
import com.devsu.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
class ClienteServiceImpl implements ClienteService {

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
        cliente.setUsuario(updateCliente.getUsuario());
        cliente.setContrasena(updateCliente.getContrasena());
        cliente.setEstado(updateCliente.getEstado());

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente patch(String clienteId, Cliente patchCliente) throws ClienteNotFoundException {
        final Cliente cliente = get(clienteId);

        if (!isEmpty(patchCliente.getNombres())) {
            cliente.setNombres(patchCliente.getNombres());
        }
        if (!isEmpty(patchCliente.getGenero())) {
            cliente.setGenero(patchCliente.getGenero());
        }
        if (!isEmpty(patchCliente.getFechaNacimiento())) {
            cliente.setFechaNacimiento(patchCliente.getFechaNacimiento());
        }
        if (!isEmpty(patchCliente.getIdentificacion())) {
            cliente.setIdentificacion(patchCliente.getIdentificacion());
        }
        if (!isEmpty(patchCliente.getDireccion())) {
            cliente.setDireccion(patchCliente.getDireccion());
        }
        if (!isEmpty(patchCliente.getTelefono())) {
            cliente.setTelefono(patchCliente.getTelefono());
        }
        if (!isEmpty(patchCliente.getUsuario())) {
            cliente.setUsuario(patchCliente.getUsuario());
        }
        if (!isEmpty(patchCliente.getContrasena())) {
            cliente.setContrasena(patchCliente.getContrasena());
        }
        if (!isEmpty(patchCliente.getEstado())) {
            cliente.setEstado(patchCliente.getEstado());
        }

        return clienteRepository.save(cliente);
    }

    @Override
    public void delete(String clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}
