package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.api.impl.CoinMarketCapApiImpl;
import com.sbrf.student.cryptoinvest.crypto.api.impl.CryptoCompareApiImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тест на {@link CryptoCompareApiImpl}.
 */
@SpringBootTest
class CryptoCompareApiImplTest {

    @Autowired
    CryptoCompareApiImpl underTest;

    @Test
    void getHourlyHistoryData() {
        var result = underTest.getHourlyHistoryData("BTC", new Date().getTime());

        assertNotNull(result);
        assertNotEquals(0, result.getData().getData().size());
    }
}
