package com.devsu.cuentas.controller;

import com.devsu.cuentas.dto.CuentaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class CuentaControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    private final CuentaDTO cuenta1 = new CuentaDTO(null, "1234", "Ahorros", BigDecimal.valueOf(0.01), true);

    private final CuentaDTO cuenta2 = new CuentaDTO(null, "5678", "Corriente", BigDecimal.valueOf(33.44), true);

    @BeforeEach
    public void setUp() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        url = "http://localhost:" + port + "/cuentas";
    }

    @Test
    void postCuenta() {
        final ResponseEntity<CuentaDTO> response = this.restTemplate.postForEntity(url, cuenta1, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();
        assertThat(response.getBody()).isEqualTo(
                new CuentaDTO(
                        response.getBody().getId(),
                        cuenta1.getNumeroCuenta(),
                        cuenta1.getTipoCuenta(),
                        cuenta1.getSaldo(),
                        cuenta1.getEstado()
                )
        );
    }

    @Test
    void getCuenta() {
        // Post cuenta
        ResponseEntity<CuentaDTO> response;
        response = this.restTemplate.postForEntity(url, cuenta1, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Get cuenta
        response = this.restTemplate.getForEntity(url + "/" + id, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new CuentaDTO(
                        id,
                        cuenta1.getNumeroCuenta(),
                        cuenta1.getTipoCuenta(),
                        cuenta1.getSaldo(),
                        cuenta1.getEstado()
                )
        );
    }

    @Test
    void putCuenta() {
        // Post cuenta
        ResponseEntity<CuentaDTO> response;
        response = this.restTemplate.postForEntity(url, cuenta1, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Put cuenta
        response = this.restTemplate.exchange(url + "/" + id, PUT, new HttpEntity<>(cuenta2), CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new CuentaDTO(
                        id,
                        cuenta2.getNumeroCuenta(),
                        cuenta2.getTipoCuenta(),
                        cuenta2.getSaldo(),
                        cuenta2.getEstado()
                )
        );
    }

    @Test
    void patchCuenta() {
        // Post cuenta
        ResponseEntity<CuentaDTO> response;
        response = this.restTemplate.postForEntity(url, cuenta1, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Patch cuenta
        final CuentaDTO cuentaPatch = new CuentaDTO(
                null,
                null,
                "nuevo tipo cuenta",
                null,
                false
        );
        response = this.restTemplate.exchange(url + "/" + id, PATCH, new HttpEntity<>(cuentaPatch), CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new CuentaDTO(
                        id,
                        cuenta1.getNumeroCuenta(),
                        "nuevo tipo cuenta",
                        cuenta1.getSaldo(),
                        false
                )
        );
    }

    @Test
    void deleteCuenta() {
        // Post cuenta
        ResponseEntity<CuentaDTO> response;
        response = this.restTemplate.postForEntity(url, cuenta1, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Delete cuenta
        response = this.restTemplate.exchange(url + "/" + id, DELETE, HttpEntity.EMPTY, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);

        // Get cuenta
        ResponseEntity<String> responseotFound = this.restTemplate.getForEntity(url + "/" + id, String.class);

        assertThat(responseotFound.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(responseotFound.getBody()).contains("Cuenta no existente");
    }

    @Test
    void postCuentaBadRequest() {
        final CuentaDTO cuentaNull = new CuentaDTO(null, null, null, null, null);
        final ResponseEntity<CuentaDTO> response = this.restTemplate.postForEntity(url, cuentaNull, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void putCuentaBadRequest() {
        // Post cuenta
        ResponseEntity<CuentaDTO> response;
        response = this.restTemplate.postForEntity(url, cuenta1, CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        final CuentaDTO cuentaNull = new CuentaDTO(null, null, null, null, null);
        response = this.restTemplate.exchange(url + "/" + id, PUT, new HttpEntity<>(cuentaNull), CuentaDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void getCuentaNotFound() {
        // Get cuenta
        final ResponseEntity<String> response = this.restTemplate.getForEntity(url + "/notfound", String.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).contains("Cuenta no existente");
    }

    @Test
    void putCuentaNotFound() {
        // Put cuenta
        final ResponseEntity<String> response = this.restTemplate.exchange(
                url + "/notfound", PUT, new HttpEntity<>(cuenta1), String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).contains("Cuenta no existente");
    }

    @Test
    void patchCuentaNotFound() {
        // Patch cuenta
        final ResponseEntity<String> response = this.restTemplate.exchange(
                url + "/notfound", PATCH, new HttpEntity<>(cuenta1), String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).contains("Cuenta no existente");
    }

    @Test
    void deleteCuentaNotFound() {
        // Delete cuenta
        final ResponseEntity<CuentaDTO> response = this.restTemplate.exchange(
                url + "/notfound", DELETE, HttpEntity.EMPTY, CuentaDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }
}