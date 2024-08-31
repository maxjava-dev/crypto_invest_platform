package com.sbrf.student.cryptoinvest.crypto.service.impl;

import com.sbrf.student.cryptoinvest.crypto.model.Cryptocurrency;
import com.sbrf.student.cryptoinvest.crypto.model.TickerPair;
import com.sbrf.student.cryptoinvest.crypto.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Реализация {@link MarketService}.
 */
@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final RestTemplate restTemplate;

    @Override
    public List<Cryptocurrency> getAllCurrencies() {

        ResponseEntity<TickerPair[]> response = restTemplate.getForEntity(BASE_API_URL + "ticker/price", TickerPair[].class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return Arrays.stream(response.getBody())
                    .map(
                            (tickerPair) -> {
                                String currency = getUSDTBasedCurrency(tickerPair.getSymbol());
                                if (currency != null) {
                                    return new Cryptocurrency(currency, tickerPair.getPrice());
                                } else {
                                    return null;
                                }
                            }
                    ).filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return null;
    }

    private String getUSDTBasedCurrency(String symbol) {
        if (symbol.endsWith("USDT") && symbol.length() > 4) {
            int pos = symbol.length() - 4;
            return symbol.substring(0, pos);
        }
        return null;
    }

    private final String BASE_API_URL = "https://api.binance.com/api/v3/";
}
