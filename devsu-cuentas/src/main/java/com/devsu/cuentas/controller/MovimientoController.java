package com.devsu.cuentas.controller;

import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.dto.MovimientoDTO;
import com.devsu.cuentas.mapper.MovimientoMapper;
import com.devsu.cuentas.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;
    private final MovimientoMapper movimientoMapper;

    @GetMapping("/{movimientoId}")
    ResponseEntity<MovimientoDTO> getMovimiento(@PathVariable String movimientoId) {
        final Movimiento movimiento = movimientoService.get(movimientoId);
        return ResponseEntity.ok(movimientoMapper.toDTO(movimiento));
    }

    @PostMapping
    ResponseEntity<MovimientoDTO> postMovimiento(@RequestBody @Valid MovimientoDTO movimientoDTO) {
        final Movimiento movimiento = movimientoMapper.toEntity(movimientoDTO);
        final Movimiento saved = movimientoService.create(movimiento);
        return new ResponseEntity<>(movimientoMapper.toDTO(saved), CREATED);
    }
}
