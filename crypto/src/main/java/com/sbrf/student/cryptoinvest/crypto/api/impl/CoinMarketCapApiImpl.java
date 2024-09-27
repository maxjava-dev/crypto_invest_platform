package com.sbrf.student.cryptoinvest.crypto.api.impl;

import com.sbrf.student.cryptoinvest.crypto.api.CoinMarketCapApi;
import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapIdList;
import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapMetadata;
import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapPriceList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Реализация {@link CoinMarketCapApi}.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CoinMarketCapApiImpl implements CoinMarketCapApi {

    private final RestTemplate restTemplate;

    @Value("${COIN_MARKET_CAP_API_KEY}")
    private String apiKey;

    @Override
    public CoinMarketCapMetadata getMetadata(List<Long> ids) {
        log.info("getMetadata called");
        try {
            ResponseEntity<CoinMarketCapMetadata> response = restTemplate.exchange(
                    BASE_API_URL + "v2/cryptocurrency/info?id={ids}",
                    HttpMethod.GET,
                    getRequestEntity(),
                    CoinMarketCapMetadata.class,
                    getIdsString(ids)
            );
            log.info("getMetadata response code: "+response.getStatusCode());
            validateResponse(response);
            return response.getBody();

        } catch (Throwable e) {
            log.error("getMetadata error", e);
            throw e;
        }
    }

    @Override
    public CoinMarketCapIdList getIdList(int topN) {
        log.info("getIdList called");

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("limit", topN);
            params.put("sort", "cmc_rank");

            ResponseEntity<CoinMarketCapIdList> response = restTemplate.exchange(
                    BASE_API_URL + "v1/cryptocurrency/map?limit={limit}&sort={sort}",
                    HttpMethod.GET,
                    getRequestEntity(),
                    CoinMarketCapIdList.class,
                    params
            );
            log.info("getIdList response code: "+response.getStatusCode());
            validateResponse(response);
            return response.getBody();

        } catch (Throwable e) {
            log.error("getIdList error", e);
            throw e;
        }
    }

    @Override
    public CoinMarketCapPriceList getPriceList(List<Long> ids) {
        log.info("getPriceList called");

        try {
            ResponseEntity<CoinMarketCapPriceList> response = restTemplate.exchange(
                    BASE_API_URL + "v2/cryptocurrency/quotes/latest?id={ids}",
                    HttpMethod.GET,
                    getRequestEntity(),
                    CoinMarketCapPriceList.class,
                    getIdsString(ids)
            );
            log.info("getPriceList response code: "+response.getStatusCode());
            validateResponse(response);
            return response.getBody();
        } catch (Throwable e) {
            log.error("getPriceList error", e);
            throw e;
        }
    }

    private void validateResponse(ResponseEntity<?> response) {
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Incorrect response from CoinMarketCap: " + response);
        }
    }

    private HttpEntity<Void> getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", apiKey);
        return new HttpEntity<>(headers);
    }

    private String getIdsString(List<Long> ids) {
        return ids.stream().map(Object::toString).collect(Collectors.joining(","));
    }
    private final String BASE_API_URL = "https://pro-api.coinmarketcap.com/";
}
