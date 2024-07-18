package com.devsu.prueba.service.implementation;

import com.devsu.prueba.cuentas.Movimiento;
import com.devsu.prueba.dto.MovimientoDTO;
import com.devsu.prueba.exception.MovimientoNotFoundException;
import com.devsu.prueba.mapper.MovimientoMapper;
import com.devsu.prueba.repository.MovimientoRepository;
import com.devsu.prueba.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoMapper movimientoMapper;
    private final MovimientoRepository movimientoRepository;

    @Override
    public Movimiento get(String movimientoId) {
        final Optional<Movimiento> movimiento = movimientoRepository.findById(movimientoId);

        if (movimiento.isPresent()) {
            return movimiento.get();
        } else {
            throw new MovimientoNotFoundException();
        }
    }

    @Override
    public Movimiento create(MovimientoDTO movimientoDTO) {
        return movimientoRepository.save(movimientoMapper.toEntity(movimientoDTO));
    }

    @Override
    public Movimiento update(String movimientoId, MovimientoDTO movimientoDTO) {
        final Movimiento movimiento = get(movimientoId);

        movimiento.setFecha(movimientoDTO.getFecha());
        movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimiento.setSaldo(movimientoDTO.getSaldo());
        movimiento.setValor(movimientoDTO.getValor());

        return movimientoRepository.save(movimiento);
    }

    @Override
    public void delete(String movimientoId) {
        movimientoRepository.deleteById(movimientoId);
    }
}
