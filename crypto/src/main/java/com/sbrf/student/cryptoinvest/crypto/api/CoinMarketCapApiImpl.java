package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.model.data.CoinMarketCapIdList;
import com.sbrf.student.cryptoinvest.crypto.model.data.CoinMarketCapMetadata;
import com.sbrf.student.cryptoinvest.crypto.model.data.CoinMarketCapPriceList;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
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
@RequiredArgsConstructor
public class CoinMarketCapApiImpl implements CoinMarketCapApi {

    private final RestTemplate restTemplate;
    private final Environment env;

    @Override
    public CoinMarketCapMetadata getMetadata(List<Long> ids) {
        ResponseEntity<CoinMarketCapMetadata> response = restTemplate.exchange(
                BASE_API_URL + "v2/cryptocurrency/info?id={ids}",
                HttpMethod.GET,
                getRequestEntity(),
                CoinMarketCapMetadata.class,
                getIdsString(ids)
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        }

        return null;
    }

    @Override
    public CoinMarketCapIdList getIdList(int topN) {

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

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        }

        return null;
    }

    @Override
    public CoinMarketCapPriceList getPriceList(List<Long> ids) {
        ResponseEntity<CoinMarketCapPriceList> response = restTemplate.exchange(
                BASE_API_URL + "v2/cryptocurrency/quotes/latest?id={ids}",
                HttpMethod.GET,
                getRequestEntity(),
                CoinMarketCapPriceList.class,
                getIdsString(ids)
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        }

        return null;
    }

    private HttpEntity<Void> getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CMC_PRO_API_KEY", env.getProperty(API_KEY_ENV_NAME));
      //  headers.set("X-CMC_PRO_API_KEY", API_KEY); // для использования локального ключа явно в классе
        return new HttpEntity<>(headers);
    }

    private String getIdsString(List<Long> ids) {
        return ids.stream().map(Object::toString).collect(Collectors.joining(","));
    }

  //  private final String API_KEY = "14d723cf-09a1-42e5-8fa5-1a8e38dc296b"; // локальный ключ
    private final String API_KEY_ENV_NAME = "COIN_MARKET_CAP_API_KEY";
    private final String BASE_API_URL = "https://pro-api.coinmarketcap.com/";
}
