package com.devsu.cuentas.service;

import com.devsu.cuentas.repository.CuentaRepository;
import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.exception.CuentaNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;

    @Override
    public Cuenta get(String cuentaId) {
        return cuentaRepository.findById(cuentaId).orElseThrow(CuentaNotFoundException::new);
    }

    @Override
    public Cuenta create(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta update(String cuentaId, Cuenta updateCuenta) {
        final Cuenta cuenta = get(cuentaId);

        cuenta.setNumeroCuenta(updateCuenta.getNumeroCuenta());
        cuenta.setTipoCuenta(updateCuenta.getTipoCuenta());
        cuenta.setSaldoInicial(updateCuenta.getSaldoInicial());
        cuenta.setEstado(updateCuenta.getEstado());

        return cuentaRepository.save(cuenta);
    }

    @Override
    public void delete(String cuentaId) {
        cuentaRepository.deleteById(cuentaId);
    }
}
