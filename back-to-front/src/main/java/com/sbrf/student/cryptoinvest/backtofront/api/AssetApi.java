package com.sbrf.student.cryptoinvest.backtofront.api;

import com.sbrf.student.cryptoinvest.backtofront.models.asset.Asset;
import com.sbrf.student.cryptoinvest.backtofront.models.asset.OperationHistoryItem;
import com.sbrf.student.cryptoinvest.backtofront.utils.RestTemplateClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * API для обращения к микросервису Активы.
 */
@Component
@Slf4j
public class AssetApi {

    @Value("${ASSETS_URL}")
    private String BASE_ASSETS_URL;

    private final RestTemplateClass restTemplate;

    @Autowired
    public AssetApi(RestTemplateClass restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Получить все активы клиента.
     * @param userId ID клиента
     * @return опциональный список активов клиента
     */
    public Optional<List<Asset>> getAssets(Long userId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>("", headers);

            ResponseEntity<Asset[]> response = restTemplate.exchange(
                BASE_ASSETS_URL + "/assets/info?userid={userid}",
                HttpMethod.GET,
                request,
                Asset[].class,
                userId
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

    /**
     * Получить историю операций клиента.
     * @param userId ID клиента
     * @return опциональный список элементов истории операции
     */
    public Optional<List<OperationHistoryItem>> getOperationHistory(Long userId) {
        try {
            ResponseEntity<OperationHistoryItem[]> response = restTemplate.getForEntity(
                BASE_ASSETS_URL + "/operations/user/" + userId, OperationHistoryItem[].class
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

    private void performCryptoOperation(String operationType, String cryptoId, String userId, String quantity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("", headers);

        restTemplate.exchange(
                BASE_ASSETS_URL + "/assets/{operationType}?cryptoid={cryptoid}&userid={userid}&quantity={quantity}",
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
