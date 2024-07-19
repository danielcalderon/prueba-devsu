package com.devsu.cuentas.controller;

import com.devsu.cuentas.dto.CuentaDTO;
import com.devsu.cuentas.dto.MovimientoDTO;
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
import java.time.LocalDateTime;

import static java.math.BigDecimal.ONE;
import static java.time.LocalDateTime.now;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MovimientoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    private MovimientoDTO deposito;

    private MovimientoDTO retiro;

    @BeforeEach
    public void setUp() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        url = "http://localhost:" + port + "/movimientos";

        ResponseEntity<CuentaDTO> response;

        final CuentaDTO cuenta1 = new CuentaDTO(null, "1234", "Ahorros", BigDecimal.valueOf(100), true);
        response = this.restTemplate.postForEntity("http://localhost:" + port + "/cuentas", cuenta1, CuentaDTO.class);

        deposito = new MovimientoDTO(
                null,
                requireNonNull(response.getBody()).getId(),
                LocalDateTime.of(2024, 6, 12, 13, 23, 56),
                "DEPOSITO",
                BigDecimal.valueOf(33.65),
                null
        );

        final CuentaDTO cuenta2 = new CuentaDTO(null, "5678", "Ahorros", BigDecimal.valueOf(50), true);
        response = this.restTemplate.postForEntity("http://localhost:" + port + "/cuentas", cuenta2, CuentaDTO.class);

        retiro = new MovimientoDTO(
                null,
                requireNonNull(response.getBody()).getId(),
                LocalDateTime.of(2022, 1, 3, 5, 21, 33),
                "RETIRO",
                BigDecimal.valueOf(22.10),
                null
        );
    }

    @Test
    void postMovimiento() {
        final ResponseEntity<MovimientoDTO> response = this.restTemplate.postForEntity(
                url, deposito, MovimientoDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();
        assertThat(response.getBody()).isEqualTo(
                new MovimientoDTO(
                        response.getBody().getId(),
                        deposito.getCuentaId(),
                        deposito.getFecha(),
                        deposito.getTipoMovimiento(),
                        deposito.getValor(),
                        response.getBody().getSaldo()
                )
        );
    }

    @Test
    void postMovimientoDepositoUpdatesSaldo() {
        final String cuentaId = deposito.getCuentaId();
        final String urlCuenta = "http://localhost:" + port + "/cuentas" + "/" + cuentaId;

        // Get cuenta
        ResponseEntity<CuentaDTO> responseCuenta;
        responseCuenta = this.restTemplate.getForEntity(urlCuenta, CuentaDTO.class);

        final BigDecimal saldoInicial = requireNonNull(responseCuenta.getBody()).getSaldo();

        // Post deposito
        final ResponseEntity<MovimientoDTO> responseMovimiento = this.restTemplate.postForEntity(
                url, deposito, MovimientoDTO.class
        );

        // Get cuenta
        responseCuenta = this.restTemplate.getForEntity(urlCuenta, CuentaDTO.class);
        final BigDecimal saldoMovimiento = requireNonNull(responseMovimiento.getBody()).getSaldo();
        final BigDecimal saldoCuenta = requireNonNull(responseCuenta.getBody()).getSaldo();

        assertThat(saldoMovimiento).isEqualTo(saldoInicial.add(deposito.getValor()));
        assertThat(saldoCuenta).isEqualTo(saldoInicial.add(deposito.getValor()));
    }

    @Test
    void postMovimientoRetiroUpdatesSaldo() {
        final String cuentaId = retiro.getCuentaId();
        final String urlCuenta = "http://localhost:" + port + "/cuentas" + "/" + cuentaId;

        // Get cuenta
        ResponseEntity<CuentaDTO> responseCuenta;
        responseCuenta = this.restTemplate.getForEntity(urlCuenta, CuentaDTO.class);

        final BigDecimal saldoInicial = requireNonNull(responseCuenta.getBody()).getSaldo();

        // Post deposito
        final ResponseEntity<MovimientoDTO> responseMovimiento = this.restTemplate.postForEntity(
                url, retiro, MovimientoDTO.class
        );

        // Get cuenta
        responseCuenta = this.restTemplate.getForEntity(urlCuenta, CuentaDTO.class);
        final BigDecimal saldoMovimiento = requireNonNull(responseMovimiento.getBody()).getSaldo();
        final BigDecimal saldoCuenta = requireNonNull(responseCuenta.getBody()).getSaldo();

        assertThat(saldoMovimiento).isEqualTo(saldoInicial.subtract(retiro.getValor()));
        assertThat(saldoCuenta).isEqualTo(saldoInicial.subtract(retiro.getValor()));
    }

    @Test
    void getMovimiento() {
        // Post movimiento
        ResponseEntity<MovimientoDTO> response;
        response = this.restTemplate.postForEntity(url, deposito, MovimientoDTO.class);

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
                        deposito.getCuentaId(),
                        deposito.getFecha(),
                        deposito.getTipoMovimiento(),
                        deposito.getValor(),
                        response.getBody().getSaldo()
                )
        );
    }

    @Test
    void putMovimientoNotImplemented() {
        // Post movimiento
        ResponseEntity<MovimientoDTO> response;
        response = this.restTemplate.postForEntity(url, deposito, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Put movimiento
        response = this.restTemplate.exchange(url + "/" + id, PUT, new HttpEntity<>(deposito), MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(METHOD_NOT_ALLOWED);
    }

    @Test
    void patchMovimientoNotImplemented() {
        // Post movimiento
        ResponseEntity<MovimientoDTO> response;
        response = this.restTemplate.postForEntity(url, deposito, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Patch movimiento
        response = this.restTemplate.exchange(url + "/" + id, PATCH, new HttpEntity<>(deposito), MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(METHOD_NOT_ALLOWED);
    }

    @Test
    void deleteMovimientoNotImplemented() {
        // Post movimiento
        ResponseEntity<MovimientoDTO> response;
        response = this.restTemplate.postForEntity(url, deposito, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull().isNotEmpty();

        final String id = response.getBody().getId();

        // Delete movimiento
        response = this.restTemplate.exchange(url + "/" + id, DELETE, HttpEntity.EMPTY, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(METHOD_NOT_ALLOWED);
    }

    @Test
    void postMovimientoBadRequest() {
        final MovimientoDTO movimientoNull = new MovimientoDTO(null, "c", now(), "tipo_invalido", ONE, ONE);
        final ResponseEntity<MovimientoDTO> response = this.restTemplate.postForEntity(url, movimientoNull, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void getMovimientoNotFound() {
        // Get movimiento
        final ResponseEntity<MovimientoDTO> response = this.restTemplate.getForEntity(url + "/notfound", MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }
}