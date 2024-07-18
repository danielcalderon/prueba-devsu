package com.devsu.prueba.service.implementation;

import com.devsu.prueba.cuentas.Cuenta;
import com.devsu.prueba.dto.CuentaDTO;
import com.devsu.prueba.exception.CuentaNotFoundException;
import com.devsu.prueba.mapper.CuentaMapper;
import com.devsu.prueba.repository.CuentaRepository;
import com.devsu.prueba.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaMapper cuentaMapper;
    private final CuentaRepository cuentaRepository;

    @Override
    public Cuenta get(String cuentaId) {
        final Optional<Cuenta> cuenta = cuentaRepository.findById(cuentaId);

        if (cuenta.isPresent()) {
            return cuenta.get();
        } else {
            throw new CuentaNotFoundException();
        }
    }

    @Override
    public Cuenta create(CuentaDTO cuentaDTO) {
        return cuentaRepository.save(cuentaMapper.toEntity(cuentaDTO));
    }

    @Override
    public Cuenta update(String cuentaId, CuentaDTO cuentaDTO) {
        final Cuenta cuenta = get(cuentaId);

        cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuenta.setEstado(cuentaDTO.getEstado());

        return cuentaRepository.save(cuenta);
    }

    @Override
    public void delete(String cuentaId) {
        cuentaRepository.deleteById(cuentaId);
    }
}
