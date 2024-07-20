package com.devsu.clientes.service;

import com.devsu.clientes.domain.ItemReporte;
import com.devsu.clientes.dto.ItemReporteDTO;
import com.devsu.clientes.dto.ReporteDTO;
import com.devsu.clientes.repository.ItemReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ItemReporteRepository itemReporteRepository;

    @Override
    public ReporteDTO creaReporte(String clienteId, LocalDate desde, LocalDate hasta) {
        log.info("Solicitud de reporte del cliente {} desde {} hasta {}", clienteId, desde, hasta);

        try {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            final List<ItemReporte> itemsReporte = itemReporteRepository.findByClienteIdAndFechaBetweenOrderByFecha(
                    clienteId, desde.atStartOfDay(), hasta.atStartOfDay()
            );

            final List<ItemReporteDTO> items = itemsReporte.stream()
                    .map(item -> new ItemReporteDTO(
                            item.getFecha().format(dateTimeFormatter),
                            item.getClienteNombres(),
                            item.getNumeroCuenta(),
                            item.getTipoCuenta(),
                            item.getSaldoInicial(),
                            item.getEstado(),
                            item.getMovimiento(),
                            item.getSaldoDisponible())
                    ).toList();

            return new ReporteDTO(items);
        } catch (Exception e) {
            log.error("Error al generar el reporte del cliente {}", clienteId, e);
            throw e;
        }
    }
}
