package com.devsu.clientes.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteTest {

    @Test
    void testClienteConstructor() {
        final Cliente cliente = new Cliente();
        assertThat(cliente).isNotNull();
    }

    @Test
    void testClienteData() {
        final String id = "clienteId";
        final String nombres = "Jaime";
        final String genero = "M";
        final LocalDate fechaNacimiento = LocalDate.now();
        final String identificacion = "identificacion";
        final String direccion = "Mi casa";
        final String telefono = "4423425";
        final String usuario = "user";
        final String contrasena = "password";
        final Boolean estado = true;

        final Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombres(nombres);
        cliente.setGenero(genero);
        cliente.setFechaNacimiento(fechaNacimiento);
        cliente.setIdentificacion(identificacion);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);
        cliente.setUsuario(usuario);
        cliente.setContrasena(contrasena);
        cliente.setEstado(estado);

        assertThat(cliente.getId()).isEqualTo(id);
        assertThat(cliente.getNombres()).isEqualTo(nombres);
        assertThat(cliente.getGenero()).isEqualTo(genero);
        assertThat(cliente.getFechaNacimiento()).isEqualTo(fechaNacimiento);
        assertThat(cliente.getIdentificacion()).isEqualTo(identificacion);
        assertThat(cliente.getDireccion()).isEqualTo(direccion);
        assertThat(cliente.getTelefono()).isEqualTo(telefono);
        assertThat(cliente.getUsuario()).isEqualTo(usuario);
        assertThat(cliente.getContrasena()).isEqualTo(contrasena);
        assertThat(cliente.getEstado()).isEqualTo(estado);
    }

    @Test
    void testClienteExtendsPersona() {
        final Cliente cliente = new Cliente();
        assertThat(cliente).isInstanceOf(Persona.class);
    }
}