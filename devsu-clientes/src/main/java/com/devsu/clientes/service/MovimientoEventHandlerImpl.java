package com.devsu.clientes.service;

import com.devsu.clientes.domain.Cliente;
import com.devsu.clientes.domain.ItemReporte;
import com.devsu.clientes.event.MovimientoEvent;
import com.devsu.clientes.exception.ClienteNotFoundException;
import com.devsu.clientes.repository.ClienteRepository;
import com.devsu.clientes.repository.ItemReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
class MovimientoEventHandlerImpl implements MovimientoEventHandler {

    private final ClienteRepository clienteRepository;

    private final ItemReporteRepository itemReporteRepository;

    @Override
    @RabbitListener(queues = "${amqp.queue.movimientos}")
    public void handleMovimientoRealizado(MovimientoEvent movimiento) {
        final String numeroCuenta = movimiento.getNumeroCuenta();
        log.info("Movimiento realizado con valor {} en la cuenta {}", movimiento.getMovimiento(), numeroCuenta);

        try {
            final String clienteId = movimiento.getClienteId();
            final Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNotFoundException::new);

            final ItemReporte itemReporte = new ItemReporte();
            itemReporte.setClienteId(cliente.getId());
            itemReporte.setFecha(LocalDateTime.parse(movimiento.getFecha()));
            itemReporte.setClienteNombres(cliente.getNombres());
            itemReporte.setNumeroCuenta(movimiento.getNumeroCuenta());
            itemReporte.setTipoCuenta(movimiento.getTipoCuenta());
            itemReporte.setSaldoInicial(movimiento.getSaldoInicial());
            itemReporte.setEstado(movimiento.getEstado());
            itemReporte.setMovimiento(movimiento.getMovimiento());
            itemReporte.setSaldoDisponible(movimiento.getSaldoDisponible());

            itemReporteRepository.save(itemReporte);
        } catch (Exception e) {
            log.error("Error al procesar el evento de movimiento de la cuenta {}", numeroCuenta, e);
        }
    }
}
