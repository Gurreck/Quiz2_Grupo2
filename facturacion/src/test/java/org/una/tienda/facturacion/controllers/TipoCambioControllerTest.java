package org.una.tienda.facturacion.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

/**
 *
 * @author Esteban Vargas
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TipoCambioControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void seCalculaCorrectamenteTipoDeCambioADolares() {
        String valorEsperado = "10.0";
        String valorEnColones = "6100";
        String resultadoApi = this.restTemplate.getForObject("http://localhost:" + port + "/tipo-cambio/a-dolares/" + valorEnColones, String.class);

        Assertions.assertEquals(resultadoApi, valorEsperado, "El tipo de cambio no se calcula correctamente");
    }

}
