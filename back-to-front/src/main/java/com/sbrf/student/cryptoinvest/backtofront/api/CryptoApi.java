package com.sbrf.student.cryptoinvest.backtofront.api;

import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoCurrency;
import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoHistoryItem;
import com.sbrf.student.cryptoinvest.backtofront.utils.RestTemplateClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * API для обращения к микросервису Криптовалюты.
 */
@Component
@Slf4j
public class CryptoApi {

    @Value("${CRYPTO_URL}")
    private String BASE_CRYPTO_URL;

    private final RestTemplateClass restTemplate;

    @Autowired
    public CryptoApi(RestTemplateClass restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Получить все криптовалюты.
     * @return опциональный список криптовалют
     */
    public Optional<List<CryptoCurrency>> getAll() {
        try {
            ResponseEntity<CryptoCurrency[]> response = restTemplate.getForEntity(
                BASE_CRYPTO_URL + "/crypto/getAll/", CryptoCurrency[].class
            );
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return Optional.of(Arrays.stream(response.getBody()).toList());
            } else {
                return Optional.empty();
            }
        } catch (Throwable e) {
            log.error(e.toString());
            return Optional.empty();
        }
    }

    /**
     * Получить одну криптовалюту.
     * @return опциональная криптовалюта
     */
    public Optional<CryptoCurrency> getOne(String symbol) {
        try {
            ResponseEntity<CryptoCurrency> response = restTemplate.getForEntity(
                    BASE_CRYPTO_URL + "/crypto/symbol/"+symbol, CryptoCurrency.class
            );
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return Optional.of(response.getBody());
            } else {
                return Optional.empty();
            }
        } catch (Throwable e) {
            log.error(e.toString());
            return Optional.empty();
        }
    }

    /**
     * Получить историю цен.
     * @param symbol символ криптовалюты
     * @return опциональный список цен
     */
    public Optional<List<CryptoHistoryItem>> getHistory(String symbol) {
        try {
            ResponseEntity<CryptoHistoryItem[]> response = restTemplate.getForEntity(BASE_CRYPTO_URL + "/crypto/history/" + symbol, CryptoHistoryItem[].class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return Optional.of(Arrays.stream(response.getBody()).toList());
            } else {
                return Optional.empty();
            }
        } catch (Throwable e) {
            log.error(e.toString());
            return Optional.empty();
        }
    }
}
