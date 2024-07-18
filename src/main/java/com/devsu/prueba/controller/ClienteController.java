package com.devsu.prueba.controller;

import com.devsu.prueba.clientes.Cliente;
import com.devsu.prueba.dto.ClienteDTO;
import com.devsu.prueba.exception.ClienteNotFoundException;
import com.devsu.prueba.mapper.ClienteMapper;
import com.devsu.prueba.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @GetMapping("/{clienteId}")
    ResponseEntity<ClienteDTO> getCliente(@PathVariable String clienteId) {
        try {
            final Cliente cliente = clienteService.get(clienteId);
            return ResponseEntity.ok(clienteMapper.toDTO(cliente));
        } catch (ClienteNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping
    ResponseEntity<ClienteDTO> postCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        final Cliente cliente = clienteMapper.toEntity(clienteDTO);
        final Cliente saved = clienteService.create(cliente);
        return ResponseEntity.ok(clienteMapper.toDTO(saved));
    }

    @PutMapping("/{clienteId}")
    ResponseEntity<ClienteDTO> putCliente(@PathVariable String clienteId, @RequestBody @Valid ClienteDTO clienteDTO) {
        try {
            final Cliente cliente = clienteMapper.toEntity(clienteDTO);
            final Cliente saved = clienteService.update(clienteId, cliente);
            return ResponseEntity.ok(clienteMapper.toDTO(saved));
        } catch (ClienteNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @DeleteMapping("/{clienteId}")
    ResponseEntity<ClienteDTO> deleteCliente(@PathVariable String clienteId) {
        clienteService.delete(clienteId);
        return ResponseEntity.ok(null);
    }
}
