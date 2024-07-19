package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.exception.CuentaNotFoundException;
import com.devsu.cuentas.exception.MovimientoNotFoundException;
import com.devsu.cuentas.repository.CuentaRepository;
import com.devsu.cuentas.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    public Movimiento create(Movimiento movimiento) {
        final String cuentaId = movimiento.getCuenta().getId();
        final Cuenta cuenta = cuentaRepository.findById(cuentaId).orElseThrow(CuentaNotFoundException::new);

        final BigDecimal nuevoSaldo = switch (movimiento.getTipoMovimiento()) {
            case DEPOSITO -> cuenta.getSaldo().add(movimiento.getValor());
            case RETIRO -> cuenta.getSaldo().subtract(movimiento.getValor());
        };

        cuenta.setSaldo(nuevoSaldo);
        movimiento.setSaldo(nuevoSaldo);

        cuentaRepository.save(cuenta);
        return movimientoRepository.save(movimiento);
    }
}
