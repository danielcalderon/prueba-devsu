package com.devsu.prueba.mapper;

import com.devsu.prueba.cuentas.Movimiento;
import com.devsu.prueba.dto.MovimientoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    Movimiento toEntity(MovimientoDTO movimientoDTO);

    MovimientoDTO toDTO(Movimiento movimiento);
}
