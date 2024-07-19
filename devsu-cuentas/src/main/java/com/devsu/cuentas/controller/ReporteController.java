package com.devsu.cuentas.controller;

import com.devsu.cuentas.dto.ReporteDTO;
import com.devsu.cuentas.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final MovimientoService movimientoService;

    @GetMapping
    ResponseEntity<ReporteDTO> getReporte(
            @RequestParam LocalDate fechaDesde,
            @RequestParam LocalDate fechaHasta,
            @RequestParam String cliente) {
        return ResponseEntity.ok(movimientoService.createReport(fechaDesde, fechaHasta, cliente));
    }
}
