package com.devsu.prueba.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class ClienteDTO {
    String id;
    @NotBlank
    String nombres;
    @NotBlank
    String genero;
    @NotNull
    LocalDate fechaNacimiento;
    @NotBlank
    String identificacion;
    @NotBlank
    String direccion;
    @NotBlank
    String telefono;
    @NotBlank
    String clienteId;
    @NotBlank
    String contrasena;
    @NotNull
    Boolean estado;
}
