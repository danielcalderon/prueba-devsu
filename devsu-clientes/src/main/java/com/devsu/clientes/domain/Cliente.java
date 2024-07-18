package com.devsu.clientes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Cliente extends Persona {

    @Column
    private String usuario;

    @Column
    private String contrasena;

    @Column
    private Boolean estado;
}
