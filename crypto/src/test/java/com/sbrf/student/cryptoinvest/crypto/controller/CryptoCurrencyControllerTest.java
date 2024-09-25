package com.sbrf.student.cryptoinvest.crypto.controller;

import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;
import com.sbrf.student.cryptoinvest.crypto.model.HistoryItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тест на {@link CryptoCurrencyController}.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoCurrencyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getOneByIdSuccess() {

        var result = restTemplate.getForEntity("http://localhost:"+port+"/crypto/1/", CryptoCurrency.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void getOneByIdNotFound() {

        var result = restTemplate.getForEntity("http://localhost:"+port+"/crypto/1000000000/", CryptoCurrency.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getOneBySymbolSuccess() {

        var result = restTemplate.getForEntity("http://localhost:"+port+"/crypto/symbol/BTC/", CryptoCurrency.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void getOneBySymbolNotFound() {

        var result = restTemplate.getForEntity("http://localhost:"+port+"/crypto/symol/not_exists/", CryptoCurrency.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getAll() {
        var result = restTemplate.getForEntity("http://localhost:"+port+"/crypto/getAll", CryptoCurrency[].class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotEquals(0, result.getBody().length);
    }

    @Test
    void getHistory() {

        var result = restTemplate.getForEntity("http://localhost:"+port+"/crypto/history/BTC/", HistoryItem[].class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }
}