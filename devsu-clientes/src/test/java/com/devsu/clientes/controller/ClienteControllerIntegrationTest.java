package com.devsu.clientes.controller;

import com.devsu.clientes.dto.ClienteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ClienteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    private final ClienteDTO cliente1 = new ClienteDTO(
            null,
            "Jose Lema",
            "H",
            LocalDate.of(1988, 5, 22),
            "15136457",
            "Echeverria 7960",
            "+328 8289 444",
            "joselema",
            "password",
            true
    );

    private final ClienteDTO cliente2 = new ClienteDTO(
            null,
            "Maria Osorio",
            "M",
            LocalDate.of(2000, 7, 10),
            "7565634",
            "Triunvirato 111",
            "0024359685469",
            "maria",
            "osorio1234",
            false
    );

    @BeforeEach
    public void setUp() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        url = "http://localhost:" + port + "/clientes";
    }

    @Test
    void postCliente() {
        final ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(url, cliente1, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();
        assertThat(response.getBody()).isEqualTo(
                new ClienteDTO(
                        response.getBody().getId(),
                        cliente1.getNombres(),
                        cliente1.getGenero(),
                        cliente1.getFechaNacimiento(),
                        cliente1.getIdentificacion(),
                        cliente1.getDireccion(),
                        cliente1.getTelefono(),
                        cliente1.getUsuario(),
                        cliente1.getContrasena(),
                        cliente1.getEstado()
                )
        );
    }

    @Test
    void getCliente() {
        // Post cliente
        ResponseEntity<ClienteDTO> response;
        response = this.restTemplate.postForEntity(url, cliente1, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Get cliente
        response = this.restTemplate.getForEntity(url + "/" + id, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new ClienteDTO(
                        id,
                        cliente1.getNombres(),
                        cliente1.getGenero(),
                        cliente1.getFechaNacimiento(),
                        cliente1.getIdentificacion(),
                        cliente1.getDireccion(),
                        cliente1.getTelefono(),
                        cliente1.getUsuario(),
                        cliente1.getContrasena(),
                        cliente1.getEstado()
                )
        );
    }

    @Test
    void putCliente() {
        // Post cliente
        ResponseEntity<ClienteDTO> response;
        response = this.restTemplate.postForEntity(url, cliente1, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Put cliente
        response = this.restTemplate.exchange(url + "/" + id, PUT, new HttpEntity<>(cliente2), ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new ClienteDTO(
                        id,
                        cliente2.getNombres(),
                        cliente2.getGenero(),
                        cliente2.getFechaNacimiento(),
                        cliente2.getIdentificacion(),
                        cliente2.getDireccion(),
                        cliente2.getTelefono(),
                        cliente2.getUsuario(),
                        cliente2.getContrasena(),
                        cliente2.getEstado()
                )
        );
    }

    @Test
    void patchCliente() {
        // Post cliente
        ResponseEntity<ClienteDTO> response;
        response = this.restTemplate.postForEntity(url, cliente1, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Patch cliente
        final ClienteDTO clientePatch = new ClienteDTO(
                null,
                null,
                null,
                null,
                null,
                "nueva direccion",
                "nuevo telefono",
                null,
                null,
                null
        );
        response = this.restTemplate.exchange(url + "/" + id, PATCH, new HttpEntity<>(clientePatch), ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new ClienteDTO(
                        id,
                        cliente1.getNombres(),
                        cliente1.getGenero(),
                        cliente1.getFechaNacimiento(),
                        cliente1.getIdentificacion(),
                        "nueva direccion",
                        "nuevo telefono",
                        cliente1.getUsuario(),
                        cliente1.getContrasena(),
                        cliente1.getEstado()
                )
        );
    }

    @Test
    void deleteCliente() {
        // Post cliente
        ResponseEntity<ClienteDTO> response;
        response = this.restTemplate.postForEntity(url, cliente1, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Delete cliente
        response = this.restTemplate.exchange(url + "/" + id, DELETE, HttpEntity.EMPTY, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);

        // Get cliente
        ResponseEntity<String> responseNotFound = this.restTemplate.getForEntity(url + "/" + id, String.class);

        assertThat(responseNotFound.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(responseNotFound.getBody()).contains("Cliente no existente");
    }

    @Test
    void postClienteBadRequest() {
        final ClienteDTO clienteNull = new ClienteDTO(null, null, null, null, null, null, null, null, null, null);
        final ResponseEntity<ClienteDTO> response = this.restTemplate.postForEntity(url, clienteNull, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void putClienteBadRequest() {
        // Post cliente
        ResponseEntity<ClienteDTO> response;
        response = this.restTemplate.postForEntity(url, cliente1, ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        final ClienteDTO clienteNull = new ClienteDTO(null, null, null, null, null, null, null, null, null, null);
        response = this.restTemplate.exchange(url + "/" + id, PUT, new HttpEntity<>(clienteNull), ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void getClienteNotFound() {
        // Get cliente
        final ResponseEntity<String> response = this.restTemplate.getForEntity(url + "/notfound", String.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).contains("Cliente no existente");
    }

    @Test
    void putClienteNotFound() {
        // Put cliente
        final ResponseEntity<String> response = this.restTemplate.exchange(
                url + "/notfound", PUT, new HttpEntity<>(cliente1), String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).contains("Cliente no existente");
    }

    @Test
    void patchClienteNotFound() {
        // Patch cliente
        final ResponseEntity<String> response = this.restTemplate.exchange(
                url + "/notfound", PATCH, new HttpEntity<>(cliente1), String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).contains("Cliente no existente");
    }

    @Test
    void deleteClienteNotFound() {
        // Delete cliente
        final ResponseEntity<ClienteDTO> response = this.restTemplate.exchange(
                url + "/notfound", DELETE, HttpEntity.EMPTY, ClienteDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }
}