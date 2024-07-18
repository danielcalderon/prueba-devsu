package com.devsu.cuentas.service;

import com.devsu.cuentas.repository.MovimientoRepository;
import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.exception.MovimientoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;

    @Override
    public Movimiento get(String movimientoId) {
        return movimientoRepository.findById(movimientoId).orElseThrow(MovimientoNotFoundException::new);
    }

    @Override
    public Movimiento create(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public Movimiento update(String movimientoId, Movimiento updateMovimiento) {
        final Movimiento movimiento = get(movimientoId);

        movimiento.setFecha(updateMovimiento.getFecha());
        movimiento.setTipoMovimiento(updateMovimiento.getTipoMovimiento());
        movimiento.setSaldo(updateMovimiento.getSaldo());
        movimiento.setValor(updateMovimiento.getValor());

        return movimientoRepository.save(movimiento);
    }

    @Override
    public void delete(String movimientoId) {
        movimientoRepository.deleteById(movimientoId);
    }
}