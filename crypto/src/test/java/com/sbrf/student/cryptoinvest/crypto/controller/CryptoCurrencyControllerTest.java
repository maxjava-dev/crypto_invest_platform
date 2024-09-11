package com.sbrf.student.cryptoinvest.crypto.controller;

import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Интеграционный тест на {@link CryptoCurrencyController}.
 */
@SpringBootTest
class CryptoCurrencyControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void getOne() {

        var result = restTemplate.getForEntity("http://localhost:8083/crypto/1/", CryptoCurrency.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void getAll() {
        var result = restTemplate.getForEntity("http://localhost:8083/crypto/getAll", CryptoCurrency[].class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertNotEquals(0, result.getBody().length);
    }
}