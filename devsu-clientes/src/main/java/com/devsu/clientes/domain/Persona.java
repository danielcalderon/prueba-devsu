package com.devsu.clientes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.UUID;

@Data
@MappedSuperclass
public class Persona {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @Column
    private String nombres;

    @Column
    private String genero;

    @Column
    private LocalDate fechaNacimiento;

    @Column
    private String identificacion;

    @Column
    private String direccion;

    @Column
    private String telefono;
}
