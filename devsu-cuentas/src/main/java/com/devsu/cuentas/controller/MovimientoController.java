package com.devsu.cuentas.controller;

import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.dto.MovimientoDTO;
import com.devsu.cuentas.exception.MovimientoNotFoundException;
import com.devsu.cuentas.mapper.MovimientoMapper;
import com.devsu.cuentas.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

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
        final Movimiento movimiento = movimientoMapper.toEntity(movimientoDTO);
        final Movimiento saved = movimientoService.create(movimiento);
        return new ResponseEntity<>(movimientoMapper.toDTO(saved), CREATED);
    }

    @PutMapping("/{movimientoId}")
    ResponseEntity<MovimientoDTO> putMovimiento(
            @PathVariable String movimientoId,
            @RequestBody @Valid MovimientoDTO movimientoDTO) {
        try {
            final Movimiento movimiento = movimientoMapper.toEntity(movimientoDTO);
            final Movimiento saved = movimientoService.update(movimientoId, movimiento);
            return ResponseEntity.ok(movimientoMapper.toDTO(saved));
        } catch (MovimientoNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @PatchMapping("/{movimientoId}")
    ResponseEntity<MovimientoDTO> patchMovimiento(
            @PathVariable String movimientoId,
            @RequestBody MovimientoDTO movimientoDTO) {
        try {
            final Movimiento movimiento = movimientoMapper.toEntity(movimientoDTO);
            final Movimiento saved = movimientoService.patch(movimientoId, movimiento);
            return ResponseEntity.ok(movimientoMapper.toDTO(saved));
        } catch (MovimientoNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @DeleteMapping("/{movimientoId}")
    ResponseEntity<MovimientoDTO> deleteMovimiento(@PathVariable String movimientoId) {
        movimientoService.delete(movimientoId);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
