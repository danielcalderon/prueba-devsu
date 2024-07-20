package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.event.MovimientoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class EstadisticaServiceImpl implements EstadisticaService {

    private final AmqpTemplate amqpTemplate;

    private final String movimientosTopicExchange;

    public EstadisticaServiceImpl(
            @Value("${amqp.exchange.movimientos}") String movimientosTopicExchange, AmqpTemplate amqpTemplate) {
        this.movimientosTopicExchange = movimientosTopicExchange;
        this.amqpTemplate = amqpTemplate;
    }

    @Override
    public void movimientoCreado(Movimiento movimiento, Cuenta cuenta) {
        try {
            final MovimientoEvent movimientoEvent = new MovimientoEvent(
                    movimiento.getFecha().toString(),
                    cuenta.getClienteId(),
                    cuenta.getNumeroCuenta(),
                    cuenta.getTipoCuenta(),
                    movimiento.getSaldoInicial().toString(),
                    cuenta.getEstado().toString(),
                    movimiento.getValor().toString(),
                    movimiento.getSaldoFinal().toString()
            );

            log.debug("Envio evento movimiento al exchange {}", movimientosTopicExchange);
            amqpTemplate.convertAndSend(movimientosTopicExchange, "movimiento", movimientoEvent);

            log.info("Movimiento event {} enviado correctamente al broker", movimiento.getId());
        } catch (Exception e) {
            log.error("Error al enviar el eventi del movimiento {}", movimiento.getId(), e);
        }
    }
}
