package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.service.MarketService;
import com.sbrf.student.cryptoinvest.crypto.service.impl.MarketServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест на {@link CoinMarketCapApiImpl}.
 */
@SpringBootTest
class CoinMarketCapApiImplTest {

    @Autowired
    CoinMarketCapApi underTest;

    @Test
    void getMetadata() {

        var result = underTest.getMetadata(List.of(1L));

        assertNotNull(result);
        assertEquals("BTC", result.getData().get("1").getSymbol());
    }

    @Test
    void getIdList() {

        var result = underTest.getIdList(10);

        assertNotNull(result);
        assertEquals(10, result.getData().size());
    }

    @Test
    void getPriceList() {

        var result = underTest.getPriceList(List.of(1L));
        var priceOfBtc = result.getData().get("1").getQuote().get("USD").getPrice();

        assertNotNull(result);
        assertNotNull(priceOfBtc);
        assertNotEquals(BigDecimal.ZERO, priceOfBtc);
    }
}