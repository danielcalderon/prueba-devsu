package com.devsu.prueba.controller;

import com.devsu.prueba.cuentas.Movimiento;
import com.devsu.prueba.dto.MovimientoDTO;
import com.devsu.prueba.exception.MovimientoNotFoundException;
import com.devsu.prueba.mapper.MovimientoMapper;
import com.devsu.prueba.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;
    private final MovimientoMapper movimientoMapper;

    @GetMapping("/{movimientoId}")
    ResponseEntity<MovimientoDTO> getMovimiento(@PathVariable String movimientoId) {
        try {
            final Movimiento movimiento = movimientoService.get(movimientoId);
            return ResponseEntity.ok(movimientoMapper.toDTO(movimiento));
        } catch (MovimientoNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PostMapping
    ResponseEntity<MovimientoDTO> postMovimiento(@RequestBody @Valid MovimientoDTO movimientoDTO) {
        final Movimiento movimiento = movimientoService.create(movimientoDTO);
        return ResponseEntity.ok(movimientoMapper.toDTO(movimiento));
    }

    @PutMapping("/{movimientoId}")
    ResponseEntity<MovimientoDTO> putMovimiento(
            @PathVariable String movimientoId,
            @RequestBody @Valid MovimientoDTO movimientoDTO) {
        try {
            final Movimiento movimiento = movimientoService.update(movimientoId, movimientoDTO);
            return ResponseEntity.ok(movimientoMapper.toDTO(movimiento));
        } catch (MovimientoNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @DeleteMapping("/{movimientoId}")
    ResponseEntity<MovimientoDTO> deleteMovimiento(@PathVariable String movimientoId) {
        movimientoService.delete(movimientoId);
        return ResponseEntity.ok(null);
    }
}
