package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.exception.MovimientoNotFoundException;
import com.devsu.cuentas.repository.MovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository movimientoRepository;

    @Override
    public Movimiento get(String movimientoId) {
        return movimientoRepository.findById(movimientoId).orElseThrow(MovimientoNotFoundException::new);
    }

    @Override
    @Transactional
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
    public Movimiento patch(String movimientoId, Movimiento patchMovimiento) throws MovimientoNotFoundException {
        final Movimiento movimiento = get(movimientoId);

        if (!isEmpty(patchMovimiento.getFecha())) {
            movimiento.setFecha(patchMovimiento.getFecha());
        }
        if (!isEmpty(patchMovimiento.getTipoMovimiento())) {
            movimiento.setTipoMovimiento(patchMovimiento.getTipoMovimiento());
        }
        if (!isEmpty(patchMovimiento.getSaldo())) {
            movimiento.setSaldo(patchMovimiento.getSaldo());
        }
        if (!isEmpty(patchMovimiento.getValor())) {
            movimiento.setValor(patchMovimiento.getValor());
        }

        return movimientoRepository.save(movimiento);
    }

    @Override
    public void delete(String movimientoId) {
        movimientoRepository.deleteById(movimientoId);
    }
}
