package com.devsu.clientes.service;

import com.devsu.clientes.dto.ReporteDTO;

import java.time.LocalDate;

public interface ReporteService {

    ReporteDTO creaReporte(String clienteId, LocalDate desde, LocalDate hasta);
}
