package com.sbrf.student.cryptoinvest.crypto.service.impl;

import com.sbrf.student.cryptoinvest.crypto.api.CoinMarketCapApi;
import com.sbrf.student.cryptoinvest.crypto.api.CryptoCompareApi;
import com.sbrf.student.cryptoinvest.crypto.model.data.*;
import com.sbrf.student.cryptoinvest.crypto.model.entity.CryptoCurrencyEntity;
import com.sbrf.student.cryptoinvest.crypto.repository.CryptoCurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Юнит тест на {@link CryptoCurrencyServiceImpl}.
 */
class CryptoCurrencyServiceImplUnitTest {

    @Mock
    CoinMarketCapApi coinMarketCapApi;

    @Mock
    CryptoCompareApi cryptoCompareApi;

    @Mock
    CryptoCurrencyRepository repository;

    private CryptoCurrencyServiceImpl underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CryptoCurrencyServiceImpl(coinMarketCapApi, cryptoCompareApi, repository);
        when(coinMarketCapApi.getIdList(50)).thenReturn(
                   new CoinMarketCapIdList(
                           List.of(
                                    new CoinMarketCapIdListElement(1L),
                                    new CoinMarketCapIdListElement(2L),
                                    new CoinMarketCapIdListElement(3L)
                           )
                   )
        );
        when(coinMarketCapApi.getMetadata(any())).thenReturn(
                new CoinMarketCapMetadata(
                        Map.of(
                                "1", new CoinMarketCapMetadataElement(1L, "BTC", "Bitcoin", "desc1", "pic1"),
                                "2", new CoinMarketCapMetadataElement(2L, "ETH", "Etherium", "desc2", "pic2"),
                                "3", new CoinMarketCapMetadataElement(3L, "USDT", "Tether", "desc3", "pic3")
                        )
                )
        );
        when(coinMarketCapApi.getPriceList(any())).thenReturn(
                new CoinMarketCapPriceList(
                        Map.of(
                                "1", new CoinMarketCapPriceElement(1L, Map.of("USD",
                                        new CoinMarketCapPriceQuote(BigDecimal.TEN, null, null, null, null, null))),
                                "2", new CoinMarketCapPriceElement(2L, Map.of("USD",
                                        new CoinMarketCapPriceQuote(BigDecimal.ZERO, null, null, null, null, null))),
                                "3", new CoinMarketCapPriceElement(3L, Map.of("USD",
                                        new CoinMarketCapPriceQuote(BigDecimal.ONE, null, null, null, null, null)))
                        )
                )
        );
    }

    @Test
    void getAllCryptoCurrencies() {
        Map<String, Long> idMap = new HashMap<>();
        idMap.put("BTC", 100L);
        idMap.put("ETH", 200L);
        idMap.put("USDT", 300L);
        when(repository.findAll()).thenReturn(List.of());
        when(repository.saveAll(any())).thenAnswer(i -> {
            var entityList = (List<CryptoCurrencyEntity>) i.getArguments()[0];
            Long id = 0L;
            for (CryptoCurrencyEntity entity : entityList) {
                entity.setId(idMap.get(entity.getSymbol()));
            }
            return entityList;
        });

        var result = underTest.getAllCryptoCurrencies();
        var result2 = underTest.getAllCryptoCurrencies();

        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals("BTC", result.get(0).getSymbol());
        assertEquals("Bitcoin", result.get(0).getName());
        assertEquals("desc1", result.get(0).getDescription());
        assertEquals("pic1", result.get(0).getLogo());
        assertEquals(BigDecimal.TEN, result.get(0).getPrice());

        assertEquals("ETH", result.get(1).getSymbol());
        assertEquals("Etherium", result.get(1).getName());
        assertEquals("desc2", result.get(1).getDescription());
        assertEquals("pic2", result.get(1).getLogo());
        assertEquals(BigDecimal.ZERO, result.get(1).getPrice());

        assertEquals("USDT", result.get(2).getSymbol());
        assertEquals("Tether", result.get(2).getName());
        assertEquals("desc3", result.get(2).getDescription());
        assertEquals("pic3", result.get(2).getLogo());
        assertEquals(BigDecimal.ONE, result.get(2).getPrice());

        assertEquals(result, result2);

        verify(coinMarketCapApi, times(2)).getPriceList(any());
        verify(coinMarketCapApi, times(1)).getIdList(50);
        verify(coinMarketCapApi, times(1)).getMetadata(any());
    }

    @Test
    void getCryptoCurrency() {
        var entity = new CryptoCurrencyEntity(1000L, 1L, "BTC", "Bitcoin", "desc1", "pic1");

        when(repository.findAll()).thenReturn(List.of(entity));

        var result = underTest.getCryptoCurrency(1000L);

        assertNotNull(result);

        assertEquals("BTC", result.getSymbol());
        assertEquals("Bitcoin", result.getName());
        assertEquals("desc1", result.getDescription());
        assertEquals("pic1", result.getLogo());
        assertEquals(BigDecimal.TEN, result.getPrice());

        verify(coinMarketCapApi, times(1)).getPriceList(any());
        verify(coinMarketCapApi, times(0)).getIdList(50);
        verify(coinMarketCapApi, times(0)).getMetadata(any());
    }
}