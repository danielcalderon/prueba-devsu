package com.devsu.prueba.controller;

import com.devsu.prueba.cuentas.Cuenta;
import com.devsu.prueba.dto.CuentaDTO;
import com.devsu.prueba.exception.CuentaNotFoundException;
import com.devsu.prueba.mapper.CuentaMapper;
import com.devsu.prueba.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;
    private final CuentaMapper cuentaMapper;

    @GetMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> getCuenta(@PathVariable String cuentaId) {
        try {
            final Cuenta cuenta = cuentaService.get(cuentaId);
            return ResponseEntity.ok(cuentaMapper.toDTO(cuenta));
        } catch (CuentaNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping
    ResponseEntity<CuentaDTO> postCuenta(@RequestBody @Valid CuentaDTO cuentaDTO) {
        final Cuenta cuenta = cuentaService.create(cuentaDTO);
        return ResponseEntity.ok(cuentaMapper.toDTO(cuenta));
    }

    @PutMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> putCuenta(@PathVariable String cuentaId, @RequestBody @Valid CuentaDTO cuentaDTO) {
        try {
            final Cuenta cuenta = cuentaService.update(cuentaId, cuentaDTO);
            return ResponseEntity.ok(cuentaMapper.toDTO(cuenta));
        } catch (CuentaNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @DeleteMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> deleteCuenta(@PathVariable String cuentaId) {
        cuentaService.delete(cuentaId);
        return ResponseEntity.ok(null);
    }
}
