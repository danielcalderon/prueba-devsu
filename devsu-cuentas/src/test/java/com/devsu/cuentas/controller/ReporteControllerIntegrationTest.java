package com.devsu.cuentas.controller;

import com.devsu.cuentas.dto.CuentaDTO;
import com.devsu.cuentas.dto.MovimientoDTO;
import com.devsu.cuentas.dto.ReporteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ReporteControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String urlReportes;
    private String urlCuentas;
    private String urlMovimientos;

    @BeforeEach
    public void setUp() {
        final String baseUrl = "http://localhost:" + port;

        urlReportes = baseUrl + "/reportes";
        urlCuentas = baseUrl + "/cuentas";
        urlMovimientos = baseUrl + "/movimientos";
    }

    @Test
    void getReportePorFechas() {
        // Create Cuenta
        CuentaDTO cuenta = new CuentaDTO(null, "cliente1", "1234", "Ahorros", BigDecimal.valueOf(100), true);
        cuenta = this.restTemplate.postForObject(urlCuentas, cuenta, CuentaDTO.class);

        // Post movimientos
        final MovimientoDTO m1 = creaMovimiento(cuenta.getId(), LocalDateTime.parse("2020-04-03T12:33:55"), 3.5);
        this.restTemplate.postForObject(urlMovimientos, m1, MovimientoDTO.class);

        final MovimientoDTO m2 = creaMovimiento(cuenta.getId(), LocalDateTime.parse("2022-04-03T12:33:55"), 7.5);
        this.restTemplate.postForObject(urlMovimientos, m2, MovimientoDTO.class);

        final MovimientoDTO m3 = creaMovimiento(cuenta.getId(), LocalDateTime.parse("2024-04-03T12:33:55"), 6.5);
        this.restTemplate.postForObject(urlMovimientos, m3, MovimientoDTO.class);

        final MovimientoDTO m4 = creaMovimiento(cuenta.getId(), LocalDateTime.parse("2026-04-03T12:33:55"), 4.5);
        this.restTemplate.postForObject(urlMovimientos, m4, MovimientoDTO.class);

        // Get reporte
        final String url = urlReportes + "?fechaDesde=2021-01-01&fechaHasta=2025-01-01&cliente=cliente1";
        final ResponseEntity<ReporteDTO> response = this.restTemplate.getForEntity(url, ReporteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getItems()).hasSize(2);
        assertThat(response.getBody().getItems().get(0).getFecha().getYear()).isEqualTo(2022);
        assertThat(response.getBody().getItems().get(1).getFecha().getYear()).isEqualTo(2024);
    }

    @Test
    void getReportePorCliente() {
        // Create Cuentas
        CuentaDTO cuenta1 = new CuentaDTO(null, "cliente1", "1234", "Ahorros", BigDecimal.valueOf(100), true);
        cuenta1 = this.restTemplate.postForObject(urlCuentas, cuenta1, CuentaDTO.class);

        CuentaDTO cuenta2 = new CuentaDTO(null, "cliente2", "1234", "Ahorros", BigDecimal.valueOf(100), true);
        cuenta2 = this.restTemplate.postForObject(urlCuentas, cuenta2, CuentaDTO.class);

        // Post movimientos
        final MovimientoDTO m1 = creaMovimiento(cuenta1.getId(), LocalDateTime.parse("2022-04-03T12:33:55"), 3.5);
        this.restTemplate.postForObject(urlMovimientos, m1, MovimientoDTO.class);

        final MovimientoDTO m2 = creaMovimiento(cuenta2.getId(), LocalDateTime.parse("2022-04-03T12:33:55"), 7.5);
        this.restTemplate.postForObject(urlMovimientos, m2, MovimientoDTO.class);

        // Get reporte
        final String url = urlReportes + "?fechaDesde=2021-01-01&fechaHasta=2025-01-01&cliente=cliente1";
        final ResponseEntity<ReporteDTO> response = this.restTemplate.getForEntity(url, ReporteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getItems()).hasSize(1);
        assertThat(response.getBody().getItems().get(0).getCliente()).isEqualTo("cliente1");
    }

    @Test
    void getReporteSinFechaDesde() {
        // Get reporte
        final String url = urlReportes + "?fechaHasta=2020-01-01&cliente=1234";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void getReporteSinFechaHasta() {
        // Get reporte
        final String url = urlReportes + "?fechaDesde=2020-01-01&cliente=1234";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    void getReporteSinCliente() {
        // Get reporte
        final String url = urlReportes + "?fechaDesde=2020-01-01&fechaHasta=2025-01-01";
        final ResponseEntity<String> response = this.restTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    private static MovimientoDTO creaMovimiento(String cuentaId, LocalDateTime fecha, double movimiento) {
        return new MovimientoDTO(
                null,
                cuentaId,
                fecha,
                null,
                BigDecimal.valueOf(movimiento),
                null,
                null
        );
    }
}