package com.sbrf.student.cryptoinvest.asset.service.api.cryptocurrency.Impl;

import com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice.CryptoServiceResponse;
import com.sbrf.student.cryptoinvest.asset.service.api.cryptocurrency.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Реализация сервиса для работы с внешним сервисом CryptoCurrency
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoCurrencyServiceImpl implements CryptoCurrencyService {
    private final RestTemplate restTemplate;

    @Value("${CRYPTO_URL}")
    private String CRYPTOCURRENCY_SERVICE_URL;

    /**
     * Метод для получения данных о криптовалюте по её ID
     *
     * @param cryptoId ID криптовалюты
     * @return Данные о криптовалюте
     */
    @Override
    public CryptoServiceResponse fetchCryptoData(Long cryptoId) {
        String url = CRYPTOCURRENCY_SERVICE_URL + "/crypto/" + cryptoId;

        log.info("Запрос данных о криптовалюте с ID: {}", cryptoId);

        try {
            CryptoServiceResponse response = restTemplate.getForObject(url, CryptoServiceResponse.class);
            log.info("Данные о криптовалюте с ID {} успешно получены.", cryptoId); // Логируем успешное получение данных
            return response;
        } catch (RestClientException e) {
            log.error("Не удалось получить данные о криптовалюте с ID: {}", cryptoId, e);
            throw new RuntimeException("Не удалось получить данные о криптовалюте", e);
        }
    }
}
