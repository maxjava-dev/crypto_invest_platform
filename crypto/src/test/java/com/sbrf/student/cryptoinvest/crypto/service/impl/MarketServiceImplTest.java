package com.sbrf.student.cryptoinvest.crypto.service.impl;

import com.sbrf.student.cryptoinvest.crypto.model.Cryptocurrency;
import com.sbrf.student.cryptoinvest.crypto.service.MarketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тест на {@link MarketServiceImpl}.
 */
@SpringBootTest
class MarketServiceImplTest {

    @Autowired
    MarketService service;

    @Test
    void getAllCurrencies() {

        List<Cryptocurrency> result = service.getAllCurrencies();

        assertNotNull(result);
        assertNotEquals(0, result.size());
    }
}