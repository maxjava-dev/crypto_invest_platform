package com.sbrf.student.cryptoinvest.crypto.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CryptoCompareApiImplTest {

    @Autowired
    CryptoCompareApiImpl underTest;

    @Test
    void getHourlyHistoryData() {
        var result = underTest.getHourlyHistoryData("BTC");

        assertNotNull(result);
        assertEquals(101, result.getData().getData().size());
    }
}
