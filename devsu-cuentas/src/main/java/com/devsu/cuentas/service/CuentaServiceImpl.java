package com.devsu.cuentas.service;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.exception.CuentaNotFoundException;
import com.devsu.cuentas.repository.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@RequiredArgsConstructor
class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;

    @Override
    public Cuenta get(String cuentaId) throws CuentaNotFoundException {
        return cuentaRepository.findById(cuentaId).orElseThrow(CuentaNotFoundException::new);
    }

    @Override
    public Cuenta create(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta update(String cuentaId, Cuenta updateCuenta) throws CuentaNotFoundException {
        final Cuenta cuenta = get(cuentaId);

        cuenta.setClienteId(updateCuenta.getClienteId());
        cuenta.setNumeroCuenta(updateCuenta.getNumeroCuenta());
        cuenta.setTipoCuenta(updateCuenta.getTipoCuenta());
        cuenta.setSaldo(updateCuenta.getSaldo());
        cuenta.setEstado(updateCuenta.getEstado());

        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta patch(String cuentaId, Cuenta patchCuenta) throws CuentaNotFoundException {
        final Cuenta cuenta = get(cuentaId);

        if (!isEmpty(patchCuenta.getClienteId())) {
            cuenta.setClienteId(patchCuenta.getClienteId());
        }
        if (!isEmpty(patchCuenta.getNumeroCuenta())) {
            cuenta.setNumeroCuenta(patchCuenta.getNumeroCuenta());
        }
        if (!isEmpty(patchCuenta.getTipoCuenta())) {
            cuenta.setTipoCuenta(patchCuenta.getTipoCuenta());
        }
        if (!isEmpty(patchCuenta.getSaldo())) {
            cuenta.setSaldo(patchCuenta.getSaldo());
        }
        if (!isEmpty(patchCuenta.getEstado())) {
            cuenta.setEstado(patchCuenta.getEstado());
        }

        return cuentaRepository.save(cuenta);
    }

    @Override
    public void delete(String cuentaId) {
        cuentaRepository.deleteById(cuentaId);
    }
}
