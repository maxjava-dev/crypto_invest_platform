package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.model.data.CCHistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("limit", 2000);
        params.put("key", apiKey);
        params.put("ts", toTimeStamp);
        CCHistoryResponse response = restTemplate.getForObject(
                BASE_API_URL + "?fsym={symbol}&tsym=USD&limit={limit}&api_key={key}&api_key={key}&toTs={ts}",
                CCHistoryResponse.class,
                params
        );

        log.info("getHourlyHistoryData response: "+response);

        return response;
    }

    private HttpEntity<Void> getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    private final String BASE_API_URL = "https://min-api.cryptocompare.com/data/v2/histohour";
}
