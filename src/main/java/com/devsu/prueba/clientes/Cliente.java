package com.devsu.prueba.clientes;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "persona_id")
public class Cliente extends Persona {

    private String contrasena;
    private boolean estado;
}
