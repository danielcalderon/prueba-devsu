package com.devsu.cuentas.mapper;

import com.devsu.cuentas.domain.Movimiento;
import com.devsu.cuentas.dto.MovimientoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(source = "cuentaId", target = "cuenta.id")
    @Mapping(target = "tipoMovimiento", ignore = true)
    Movimiento toEntity(MovimientoDTO movimientoDTO);

    @Mapping(source = "cuenta.id", target = "cuentaId")
    MovimientoDTO toDTO(Movimiento movimiento);
}
