package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.exception.CuentaNotFoundException;
import com.devsu.cuentas.exception.MovimientoNotFoundException;
import com.devsu.cuentas.exception.SaldoInsuficienteException;
import com.devsu.cuentas.repository.CuentaRepository;
import com.devsu.cuentas.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.devsu.cuentas.domain.TipoMovimiento.DEPOSITO;
import static com.devsu.cuentas.domain.TipoMovimiento.RETIRO;
import static java.math.BigDecimal.ZERO;

@Slf4j
@Service
@RequiredArgsConstructor
class MovimientoServiceImpl implements MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final EstadisticaService estadisticaService;

    @Override
    public Movimiento get(String movimientoId) {
        return movimientoRepository.findById(movimientoId).orElseThrow(MovimientoNotFoundException::new);
    }

    @Override
    @Transactional
    public Movimiento create(Movimiento movimiento) throws SaldoInsuficienteException {
        final String cuentaId = movimiento.getCuenta().getId();
        log.info("Nuevo movimiento con valor {} de la cuenta {}", movimiento.getValor(), cuentaId);

        try {
            final Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(CuentaNotFoundException::new);

            final BigDecimal valorMovimiento = movimiento.getValor();
            final BigDecimal saldoInicial = cuenta.getSaldo();
            final BigDecimal nuevoSaldo;

            if (valorMovimiento.compareTo(ZERO) > 0) {
                movimiento.setTipoMovimiento(DEPOSITO);
                nuevoSaldo = saldoInicial.add(valorMovimiento);
            } else {
                movimiento.setTipoMovimiento(RETIRO);
                nuevoSaldo = saldoInicial.subtract(valorMovimiento.abs());
            }

            if (nuevoSaldo.compareTo(ZERO) < 0) {
                throw new SaldoInsuficienteException();
            }

            cuenta.setSaldo(nuevoSaldo);
            cuentaRepository.save(cuenta);

            movimiento.setSaldoInicial(saldoInicial);
            movimiento.setSaldoFinal(nuevoSaldo);
            final Movimiento movimientoSaved = movimientoRepository.save(movimiento);

            // Envio evento movimiento
            estadisticaService.movimientoCreado(movimientoSaved, cuenta);

            return movimientoSaved;
        } catch (Exception e) {
            log.error("Error al registrar el movimiento de la cuenta {}", cuentaId, e);
            throw e;
        }
    }
}
