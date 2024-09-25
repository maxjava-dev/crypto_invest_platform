package com.sbrf.student.cryptoinvest.crypto.service.impl;

import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;
import com.sbrf.student.cryptoinvest.crypto.model.HistoryItem;
import com.sbrf.student.cryptoinvest.crypto.service.CryptoCurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
    void getCryptoCurrencyByIdSuccess() {

        var element = service.getCryptoCurrency(1L).get();

        assertNotNull(element.getPrice());
        assertNotNull(element.getName());
        assertNotNull(element.getSymbol());
        assertNotNull(element.getDescription());
        assertNotNull(element.getId());
    }

    @Test
    void getCryptoCurrencyByIdNotFound() {

        var element = service.getCryptoCurrency(100000000L);

        assertTrue(element.isEmpty());
    }

    @Test
    void getCryptoCurrencyBySymbolSuccess() {

        var element = service.getCryptoCurrencyBySymbol("BTC").get();

        assertNotNull(element.getPrice());
        assertNotNull(element.getName());
        assertNotNull(element.getSymbol());
        assertNotNull(element.getDescription());
        assertNotNull(element.getId());
    }

    @Test
    void getCryptoCurrencyBySymbolNotFound() {

        var element = service.getCryptoCurrencyBySymbol("NOT_EXISTING");

        assertTrue(element.isEmpty());
    }

    @Test
    void getHistoryData() {

        var result = service.getHistoryData("BTC");

        assertNotNull(result);
        assertNotEquals(0, result.size());
        for (HistoryItem element : result) {
            assertNotNull(element);
            assertNotNull(element.getPrice());
        }
    }

}