package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.api.impl.CoinMarketCapApiImpl;
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
        var first = result.getData().get("1");
        assertEquals("BTC", first.getSymbol());
        assertNotNull(first.getDescription());
        assertNotNull(first.getLogo());
        assertNotNull(first.getName());
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
        var priceOfBtc = result.getData().get("1").getQuote().get("USD");

        assertNotNull(result);
        assertNotNull(priceOfBtc);
        assertNotEquals(BigDecimal.ZERO, priceOfBtc.getPrice());
        assertNotNull(priceOfBtc.getMarketCap());
        assertNotNull(priceOfBtc.getPercentChange1h());
        assertNotNull(priceOfBtc.getPercentChange7d());
        assertNotNull(priceOfBtc.getPercentChange24h());
        assertNotNull(priceOfBtc.getPercentChange30d());
    }
}