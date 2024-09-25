package com.sbrf.student.cryptoinvest.crypto.api.impl;

import com.sbrf.student.cryptoinvest.crypto.api.CryptoCompareApi;
import com.sbrf.student.cryptoinvest.crypto.model.data.cryptocompare.CCHistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация {@link CryptoCompareApi}.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CryptoCompareApiImpl implements CryptoCompareApi {

    private final RestTemplate restTemplate;

    @Value("${CC_API_KEY}")
    private String apiKey;

    @Override
    public CCHistoryResponse getHourlyHistoryData(String symbol, Long toTimeStamp) {
        log.info("getHourlyHistoryData called");
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("symbol", symbol);
            params.put("limit", 2000);
            params.put("key", apiKey);
            params.put("ts", toTimeStamp);
            ResponseEntity<CCHistoryResponse> response = restTemplate.getForEntity(
                    BASE_API_URL + "?fsym={symbol}&tsym=USD&limit={limit}&api_key={key}&api_key={key}&toTs={ts}",
                    CCHistoryResponse.class,
                    params
            );
            log.info("getHourlyHistoryData response: " + response);
            validateResponse(response);
            return response.getBody();
        } catch (Throwable e) {
            log.error("getHourlyHistoryData error", e);
            throw e;
        }
    }

    private void validateResponse(ResponseEntity<?> response) {
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Incorrect response from CryptoCompare: " + response);
        }
    }

    private final String BASE_API_URL = "https://min-api.cryptocompare.com/data/v2/histohour";
}
