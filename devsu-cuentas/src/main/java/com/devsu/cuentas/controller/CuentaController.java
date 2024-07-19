package com.devsu.cuentas.controller;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.dto.CuentaDTO;
import com.devsu.cuentas.mapper.CuentaMapper;
import com.devsu.cuentas.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;
    private final CuentaMapper cuentaMapper;

    @GetMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> getCuenta(@PathVariable String cuentaId) {
        final Cuenta cuenta = cuentaService.get(cuentaId);
        return ResponseEntity.ok(cuentaMapper.toDTO(cuenta));
    }

    @PostMapping
    ResponseEntity<CuentaDTO> postCuenta(@RequestBody @Valid CuentaDTO cuentaDTO) {
        final Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        final Cuenta saved = cuentaService.create(cuenta);
        return new ResponseEntity<>(cuentaMapper.toDTO(saved), CREATED);
    }

    @PutMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> putCuenta(@PathVariable String cuentaId, @RequestBody @Valid CuentaDTO cuentaDTO) {
        final Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        final Cuenta saved = cuentaService.update(cuentaId, cuenta);
        return ResponseEntity.ok(cuentaMapper.toDTO(saved));
    }

    @PatchMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> patchCuenta(@PathVariable String cuentaId, @RequestBody CuentaDTO cuentaDTO) {
        final Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        final Cuenta saved = cuentaService.patch(cuentaId, cuenta);
        return ResponseEntity.ok(cuentaMapper.toDTO(saved));
    }

    @DeleteMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> deleteCuenta(@PathVariable String cuentaId) {
        cuentaService.delete(cuentaId);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
