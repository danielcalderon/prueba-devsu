package com.devsu.clientes.repository;

import com.devsu.clientes.domain.ItemReporte;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemReporteRepository extends CrudRepository<ItemReporte, String> {

    List<ItemReporte> findByClienteIdAndFechaBetweenOrderByFecha(String clienteId, LocalDateTime desde, LocalDateTime hasta);
}
