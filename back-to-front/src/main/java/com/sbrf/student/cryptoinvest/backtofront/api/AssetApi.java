package com.sbrf.student.cryptoinvest.backtofront.api;

import com.sbrf.student.cryptoinvest.backtofront.utils.RestTemplateClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

/**
 * API для обращения к микросервису Активы.
 */
@Component
@Slf4j
public class AssetApi {

    @Value("${ASSETS_URL}")
    private String BASE_CRYPTO_URL;

    private final RestTemplateClass restTemplate;

    @Autowired
    public AssetApi(RestTemplateClass restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Покупка криптовалюты.
     * @param cryptoId ID криптовалюты
     * @param userId ID клиента
     * @param quantity Количество криптовалюты на покупку
     */
    public void performBuyCrypto(String cryptoId, String userId, String quantity) {
        performCryptoOperation("buy", cryptoId, userId, quantity);
    }

    /**
     * Продажа криптовалюты.
     * @param cryptoId ID криптовалюты
     * @param userId ID клиента
     * @param quantity Количество криптовалюты на продажу
     */
    public void performSellCrypto(String cryptoId, String userId, String quantity) {
        performCryptoOperation("sell", cryptoId, userId, quantity);
    }

    private void performCryptoOperation(String operationType, String cryptoId, String userId, String quantity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("", headers);

        restTemplate.exchange(
            BASE_CRYPTO_URL + "/assets/{operationType}?cryptoid={cryptoid}&userid={userid}&quantity={quantity}",
            HttpMethod.POST,
            request,
            Void.class,
            operationType,
            cryptoId,
            userId,
            quantity
        );
    }
}
