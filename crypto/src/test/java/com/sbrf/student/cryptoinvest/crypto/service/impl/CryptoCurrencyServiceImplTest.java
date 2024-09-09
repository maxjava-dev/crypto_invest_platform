package com.sbrf.student.cryptoinvest.crypto.service.impl;

import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;
import com.sbrf.student.cryptoinvest.crypto.service.CryptoCurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Интеграционный тест на {@link CryptoCurrencyServiceImpl}.
 */
@SpringBootTest
class CryptoCurrencyServiceImplTest {

    @Autowired
    CryptoCurrencyService service;

    @Test
    void getAllCryptoCurrencies() {

        var result = service.getAllCryptoCurrencies();

        assertNotNull(result);
        assertNotEquals(0, result.size());
        for (CryptoCurrency element : result) {
            assertNotNull(element);
            assertNotNull(element.getPrice());
            assertNotNull(element.getName());
            assertNotNull(element.getSymbol());
            assertNotNull(element.getDescription());
            assertNotNull(element.getId());
        }
    }

    @Test
    void getCryptoCurrency() {

        var element = service.getCryptoCurrency(1L);

        assertNotNull(element);
        assertNotNull(element.getPrice());
        assertNotNull(element.getName());
        assertNotNull(element.getSymbol());
        assertNotNull(element.getDescription());
        assertNotNull(element.getId());
    }
}