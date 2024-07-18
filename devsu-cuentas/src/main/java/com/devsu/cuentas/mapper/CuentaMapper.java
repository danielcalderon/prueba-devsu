package com.devsu.cuentas.mapper;

import com.devsu.cuentas.domain.Cuenta;
import com.devsu.cuentas.dto.CuentaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    Cuenta toEntity(CuentaDTO cuentaDTO);

    CuentaDTO toDTO(Cuenta cuenta);
}
