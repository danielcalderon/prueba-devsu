package com.devsu.prueba.mapper;

import com.devsu.prueba.cuentas.Cuenta;
import com.devsu.prueba.dto.CuentaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    Cuenta toEntity(CuentaDTO cuentaDTO);

    CuentaDTO toDTO(Cuenta cuenta);
}
