package com.devsu.prueba.clientes;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.InheritanceType.JOINED;

@Data
@Entity
@Inheritance(strategy = JOINED)
public class Persona {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String nombres;
    private String genero;
    private LocalDate fechaNacimiento;
    private String identificacion;
    private String direccion;
    private String telefono;
}
