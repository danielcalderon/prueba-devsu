package com.devsu.cuentas.controller;

import com.devsu.cuentas.dto.MovimientoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MovimientoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    private final MovimientoDTO movimiento1 = new MovimientoDTO(
            null,
            LocalDateTime.of(2024, 6, 12, 13, 23, 56),
            "Retiro",
            BigDecimal.valueOf(0.01),
            BigDecimal.valueOf(92129.33)
    );

    private final MovimientoDTO movimiento2 = new MovimientoDTO(
            null,
            LocalDateTime.of(2022, 1, 3, 5, 21, 33),
            "Deposito",
            BigDecimal.valueOf(333.22),
            BigDecimal.valueOf(67675.65)
    );

    @BeforeEach
    public void setUp() {
        url = "http://localhost:" + port + "/movimientos";
    }

    @Test
    void postMovimiento() {
        final ResponseEntity<MovimientoDTO> response = this.restTemplate.postForEntity(url, movimiento1, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();
        assertThat(response.getBody()).isEqualTo(
                new MovimientoDTO(
                        response.getBody().getId(),
                        movimiento1.getFecha(),
                        movimiento1.getTipoMovimiento(),
                        movimiento1.getValor(),
                        movimiento1.getSaldo()
                )
        );
    }

    @Test
    void getMovimiento() {
        // Post movimiento
        ResponseEntity<MovimientoDTO> response;
        response = this.restTemplate.postForEntity(url, movimiento1, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Get movimiento
        response = this.restTemplate.getForEntity(url + "/" + id, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new MovimientoDTO(
                        id,
                        movimiento1.getFecha(),
                        movimiento1.getTipoMovimiento(),
                        movimiento1.getValor(),
                        movimiento1.getSaldo()
                )
        );
    }

    @Test
    void putMovimiento() {
        // Post movimiento
        ResponseEntity<MovimientoDTO> response;
        response = this.restTemplate.postForEntity(url, movimiento1, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Put movimiento
        response = this.restTemplate.exchange(url + "/" + id, PUT, new HttpEntity<>(movimiento2), MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new MovimientoDTO(
                        id,
                        movimiento2.getFecha(),
                        movimiento2.getTipoMovimiento(),
                        movimiento2.getValor(),
                        movimiento2.getSaldo()
                )
        );
    }

    @Test
    void deleteMovimiento() {
        // Post movimiento
        ResponseEntity<MovimientoDTO> response;
        response = this.restTemplate.postForEntity(url, movimiento1, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Delete movimiento
        response = this.restTemplate.exchange(url + "/" + id, DELETE, HttpEntity.EMPTY, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);

        // Get movimiento
        response = this.restTemplate.getForEntity(url + "/" + id, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void postMovimientoBadRequest() {
        final MovimientoDTO movimientoNull = new MovimientoDTO(null, null, null, null, null);
        final ResponseEntity<MovimientoDTO> response = this.restTemplate.postForEntity(url, movimientoNull, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void putMovimientoBadRequest() {
        // Post movimiento
        ResponseEntity<MovimientoDTO> response;
        response = this.restTemplate.postForEntity(url, movimiento1, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        final MovimientoDTO movimientoNull = new MovimientoDTO(null, null, null, null, null);
        response = this.restTemplate.exchange(url + "/" + id, PUT, new HttpEntity<>(movimientoNull), MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void getMovimientoNotFound() {
        // Get movimiento
        ResponseEntity<MovimientoDTO> response = this.restTemplate.getForEntity(url + "/notfound", MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    void deleteMovimientoNotFound() {
        // Delete movimiento
        ResponseEntity<MovimientoDTO> response = this.restTemplate.exchange(
                url + "/notfound", DELETE, HttpEntity.EMPTY, MovimientoDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT);
    }
}