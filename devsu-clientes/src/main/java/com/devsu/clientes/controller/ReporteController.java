package com.devsu.clientes.controller;

import com.devsu.clientes.dto.ReporteDTO;
import com.devsu.clientes.service.ReporteService;
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

    private final ReporteService reporteService;

    @GetMapping
    ResponseEntity<ReporteDTO> getReporte(
            @RequestParam String cliente,
            @RequestParam LocalDate fechaDesde,
            @RequestParam LocalDate fechaHasta) {
        return ResponseEntity.ok(reporteService.creaReporte(cliente, fechaDesde, fechaHasta));
    }
}
