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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.ONE;
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

    private MovimientoDTO retiroSaldoInsuficiente;

    @BeforeEach
    public void setUp() {
        url = "http://localhost:" + port + "/movimientos";

        ResponseEntity<CuentaDTO> response;

        CuentaDTO cuenta1 = new CuentaDTO(null, "cliente1", "1234", "Ahorros", BigDecimal.valueOf(100), true);
        cuenta1 = this.restTemplate.postForObject("http://localhost:" + port + "/cuentas", cuenta1, CuentaDTO.class);

        deposito = creaMovimiento(cuenta1.getId(), LocalDateTime.of(2024, 6, 12, 13, 23, 56), 33.65);

        CuentaDTO cuenta2 = new CuentaDTO(null, "cliente2", "5678", "Ahorros", BigDecimal.valueOf(50), true);
        cuenta2 = this.restTemplate.postForObject("http://localhost:" + port + "/cuentas", cuenta2, CuentaDTO.class);

        retiro = creaMovimiento(cuenta2.getId(), LocalDateTime.of(2022, 1, 3, 5, 21, 33), -22.10);

        retiroSaldoInsuficiente = creaMovimiento(cuenta2.getId(), LocalDateTime.of(2022, 1, 3, 5, 21, 33), -99999f);
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
                        "DEPOSITO",
                        deposito.getValor(),
                        response.getBody().getSaldoInicial(),
                        response.getBody().getSaldoFinal()
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
        final BigDecimal saldoMovimiento1 = requireNonNull(responseMovimiento.getBody()).getSaldoInicial();
        final BigDecimal saldoMovimiento2 = requireNonNull(responseMovimiento.getBody()).getSaldoFinal();
        final BigDecimal saldoCuenta = requireNonNull(responseCuenta.getBody()).getSaldo();

        assertThat(responseMovimiento.getBody().getTipoMovimiento()).isEqualTo("DEPOSITO");
        assertThat(saldoMovimiento1).isEqualTo(saldoInicial);
        assertThat(saldoMovimiento2).isEqualTo(saldoInicial.add(deposito.getValor()));
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

        // Post retiro
        final ResponseEntity<MovimientoDTO> responseMovimiento = this.restTemplate.postForEntity(
                url, retiro, MovimientoDTO.class
        );

        // Get cuenta
        responseCuenta = this.restTemplate.getForEntity(urlCuenta, CuentaDTO.class);
        final BigDecimal saldoMovimiento1 = requireNonNull(responseMovimiento.getBody()).getSaldoInicial();
        final BigDecimal saldoMovimiento2 = requireNonNull(responseMovimiento.getBody()).getSaldoFinal();
        final BigDecimal saldoCuenta = requireNonNull(responseCuenta.getBody()).getSaldo();

        assertThat(responseMovimiento.getBody().getTipoMovimiento()).isEqualTo("RETIRO");
        assertThat(saldoMovimiento1).isEqualTo(saldoInicial);
        assertThat(saldoMovimiento2).isEqualTo(saldoInicial.subtract(retiro.getValor().abs()));
        assertThat(saldoCuenta).isEqualTo(saldoInicial.subtract(retiro.getValor().abs()));
    }

    @Test
    void postMovimientoRetiroSaldoNoDisponible() {
        // Post retiro
        final ResponseEntity<String> response = this.restTemplate.postForEntity(
                url, retiroSaldoInsuficiente, String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(PAYMENT_REQUIRED);
        System.out.println(response.getBody());
        assertThat(response.getBody()).contains("Saldo no disponible");
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
                        "DEPOSITO",
                        deposito.getValor(),
                        response.getBody().getSaldoInicial(),
                        response.getBody().getSaldoFinal()
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
        final MovimientoDTO movimientoNull = new MovimientoDTO(null, "c", null, null, ONE, ONE, ONE);
        final ResponseEntity<MovimientoDTO> response = this.restTemplate.postForEntity(url, movimientoNull, MovimientoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void getMovimientoNotFound() {
        // Get movimiento
        final ResponseEntity<String> response = this.restTemplate.getForEntity(url + "/notfound", String.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody()).contains("Movimiento inexistente");
    }

    private static MovimientoDTO creaMovimiento(String cuentaId, LocalDateTime fecha, double valor) {
        return new MovimientoDTO(null, cuentaId, fecha, null, BigDecimal.valueOf(valor), null, null);
    }
}