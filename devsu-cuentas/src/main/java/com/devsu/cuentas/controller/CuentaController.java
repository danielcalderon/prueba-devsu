package com.devsu.cuentas.controller;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.dto.CuentaDTO;
import com.devsu.cuentas.exception.CuentaNotFoundException;
import com.devsu.cuentas.mapper.CuentaMapper;
import com.devsu.cuentas.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

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
        final Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
        final Cuenta saved = cuentaService.create(cuenta);
        return new ResponseEntity<>(cuentaMapper.toDTO(saved), CREATED);
    }

    @PutMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> putCuenta(@PathVariable String cuentaId, @RequestBody @Valid CuentaDTO cuentaDTO) {
        try {
            final Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
            final Cuenta saved = cuentaService.update(cuentaId, cuenta);
            return ResponseEntity.ok(cuentaMapper.toDTO(saved));
        } catch (CuentaNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PatchMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> patchCuenta(@PathVariable String cuentaId, @RequestBody CuentaDTO cuentaDTO) {
        try {
            final Cuenta cuenta = cuentaMapper.toEntity(cuentaDTO);
            final Cuenta saved = cuentaService.patch(cuentaId, cuenta);
            return ResponseEntity.ok(cuentaMapper.toDTO(saved));
        } catch (CuentaNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @DeleteMapping("/{cuentaId}")
    ResponseEntity<CuentaDTO> deleteCuenta(@PathVariable String cuentaId) {
        cuentaService.delete(cuentaId);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
