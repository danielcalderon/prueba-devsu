package com.devsu.cuentas.mapper;

import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.dto.MovimientoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    Movimiento toEntity(MovimientoDTO movimientoDTO);

    MovimientoDTO toDTO(Movimiento movimiento);
}
