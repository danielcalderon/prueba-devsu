package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.exception.CuentaNotFoundException;
import com.devsu.cuentas.exception.MovimientoNotFoundException;
import com.devsu.cuentas.exception.SaldoInsuficienteException;
import com.devsu.cuentas.repository.CuentaRepository;
import com.devsu.cuentas.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.devsu.cuentas.domain.TipoMovimiento.DEPOSITO;
import static com.devsu.cuentas.domain.TipoMovimiento.RETIRO;
import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
class MovimientoServiceImpl implements MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;

    @Override
    public Movimiento get(String movimientoId) {
        return movimientoRepository.findById(movimientoId).orElseThrow(MovimientoNotFoundException::new);
    }

    @Override
    @Transactional
    public Movimiento create(Movimiento movimiento) throws SaldoInsuficienteException {
        final String cuentaId = movimiento.getCuenta().getId();
        final Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(CuentaNotFoundException::new);

        final BigDecimal saldoInicial = cuenta.getSaldo();
        final BigDecimal nuevoSaldo;
        if (movimiento.getValor().compareTo(ZERO) > 0) {
            movimiento.setTipoMovimiento(DEPOSITO);
            nuevoSaldo = saldoInicial.add(movimiento.getValor());
        } else {
            final BigDecimal valorMovimiento = movimiento.getValor().abs();

            movimiento.setTipoMovimiento(RETIRO);

            if (valorMovimiento.compareTo(saldoInicial) > 0) {
                throw new SaldoInsuficienteException();
            }

            nuevoSaldo = saldoInicial.subtract(valorMovimiento);
        }

        cuenta.setSaldo(nuevoSaldo);
        movimiento.setSaldo(nuevoSaldo);

        cuentaRepository.save(cuenta);
        return movimientoRepository.save(movimiento);
    }
}
