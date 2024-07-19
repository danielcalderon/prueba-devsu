package com.devsu.clientes.controller;

import com.devsu.clientes.domain.Cliente;
import com.devsu.clientes.dto.ClienteDTO;
import com.devsu.clientes.mapper.ClienteMapper;
import com.devsu.clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @GetMapping("/{clienteId}")
    ResponseEntity<ClienteDTO> getCliente(@PathVariable String clienteId) {
        final Cliente cliente = clienteService.get(clienteId);
        return ResponseEntity.ok(clienteMapper.toDTO(cliente));
    }

    @PostMapping
    ResponseEntity<ClienteDTO> postCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
        final Cliente cliente = clienteMapper.toEntity(clienteDTO);
        final Cliente saved = clienteService.create(cliente);
        return new ResponseEntity<>(clienteMapper.toDTO(saved), CREATED);
    }

    @PutMapping("/{clienteId}")
    ResponseEntity<ClienteDTO> putCliente(@PathVariable String clienteId, @RequestBody @Valid ClienteDTO clienteDTO) {
        final Cliente cliente = clienteMapper.toEntity(clienteDTO);
        final Cliente saved = clienteService.update(clienteId, cliente);
        return ResponseEntity.ok(clienteMapper.toDTO(saved));
    }

    @PatchMapping("/{clienteId}")
    ResponseEntity<ClienteDTO> patchCliente(@PathVariable String clienteId, @RequestBody ClienteDTO clienteDTO) {
        final Cliente cliente = clienteMapper.toEntity(clienteDTO);
        final Cliente saved = clienteService.patch(clienteId, cliente);
        return ResponseEntity.ok(clienteMapper.toDTO(saved));
    }

    @DeleteMapping("/{clienteId}")
    ResponseEntity<Void> deleteCliente(@PathVariable String clienteId) {
        clienteService.delete(clienteId);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
