package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.dto.ItemReporteDTO;
import com.devsu.cuentas.dto.ReporteDTO;
import com.devsu.cuentas.exception.CuentaNotFoundException;
import com.devsu.cuentas.exception.MovimientoNotFoundException;
import com.devsu.cuentas.exception.SaldoInsuficienteException;
import com.devsu.cuentas.repository.CuentaRepository;
import com.devsu.cuentas.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

        movimiento.setSaldoInicial(saldoInicial);
        movimiento.setSaldoFinal(nuevoSaldo);
        cuenta.setSaldo(nuevoSaldo);

        cuentaRepository.save(cuenta);
        return movimientoRepository.save(movimiento);
    }

    @Override
    public ReporteDTO createReport(LocalDate desde, LocalDate hasta, String clienteId) {
        final List<Movimiento> movimientos = movimientoRepository.findByFechaAndClienteId(
                desde.atStartOfDay(), hasta.atStartOfDay(), clienteId
        );

        final List<ItemReporteDTO> items = movimientos.stream()
                .map(m -> new ItemReporteDTO(
                        m.getFecha(),
                        clienteId,
                        m.getCuenta().getId(),
                        m.getCuenta().getTipoCuenta(),
                        m.getSaldoInicial(),
                        m.getCuenta().getEstado(),
                        m.getValor(),
                        m.getSaldoFinal())
                ).toList();

        return new ReporteDTO(items);
    }
}
