package com.devsu.prueba.service.implementation;

import com.devsu.prueba.cuentas.Cuenta;
import com.devsu.prueba.exception.CuentaNotFoundException;
import com.devsu.prueba.repository.CuentaRepository;
import com.devsu.prueba.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

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
