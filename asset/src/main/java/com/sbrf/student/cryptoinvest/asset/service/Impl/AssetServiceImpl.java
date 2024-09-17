package com.sbrf.student.cryptoinvest.asset.service.Impl;

import com.sbrf.student.cryptoinvest.asset.api.cryptocurrencyservice.CryptoServiceResponse;
import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.repository.AssetRepository;
import com.sbrf.student.cryptoinvest.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация {@link AssetService}
 */

// TODO: Проверка на валидацию в контроллере реализовать, в сервисе проверка на счет и активы
@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    private final RestTemplate restTemplate;
    private final AssetRepository assetRepository;

    @Value("${CRYPTO_URL}")
    private String CRYPTOCURRENCY_SERVICE_URL;

    /**
     * Получить список активов, принадлежащих конкретному пользователю
     * @param userId ID пользователя, для которого нужно получить список активов
     * @return
     */
    @Override
    public List<Asset> getOwnedAssetsByUserId(Long userId) {
        return assetRepository.findOwnedAssetsByUserId(userId);
    }

    @Override
    public void buyAsset(Long cryptoId, Long userId, BigDecimal quantity) {
        CryptoServiceResponse response = fetchCryptoData(cryptoId);
        BigDecimal price = response.getPrice();
        BigDecimal totalCost = price.multiply(quantity);

            /**
             * Проверка существования актива для данного пользователя и криптовалюты
             */
            Asset asset = assetRepository.findByUserIdAndCryptoId(userId, cryptoId);
            if (asset == null) {
                /**
                 * Если актива нет, создается новый
                 */
                asset = new Asset();
                asset.setUserId(userId);
                asset.setCryptoId(cryptoId);
                asset.setQuantity(quantity);
            } else {
                /**
                 * Если актив существует, происходит обновление количества актива
                 */
                asset.setQuantity(asset.getQuantity().add(quantity));
            }

            /**
             * Сохранение актива в базу данных
             */
            assetRepository.save(asset);

            // TODO: Обработка исключений, проверка баланса пользователя, взаимодействие с историей операций
    }

    /**
     * Вспомогательный метод для получения криптовалюты по ID, из сервиса Cryptocurrency
     * @param cryptoId ID криптовалюты
     * @return информация о криптовалюте в формате json
     */
    private CryptoServiceResponse fetchCryptoData(Long cryptoId) {
        String url = CRYPTOCURRENCY_SERVICE_URL + "/" + cryptoId;
        try {
            return restTemplate.getForObject(url, CryptoServiceResponse.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Не удалось получить данные о криптовалюте", e);
        }
    }

    @Override
    public void sellAsset(Long cryptoId, Long userId, BigDecimal quantity) {
        CryptoServiceResponse response = fetchCryptoData(cryptoId);
        BigDecimal price = response.getPrice();
        BigDecimal totalRevenue = price.multiply(quantity);

        /**
         * Проверка наличия актива для продажи
         */
        Asset asset = assetRepository.findByUserIdAndCryptoId(userId, cryptoId);
        if (asset == null || asset.getQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("Недостаточно активов для продажи.");
        }

        /**
         * Обновление количества актива
         */
        asset.setQuantity(asset.getQuantity().subtract(quantity));

        /**
         * Удаление актива, если количество стало нулевым
         */
        if (asset.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            assetRepository.delete(asset);
        } else {
            assetRepository.save(asset);
        }

        // TODO: Обработка исключений, проверка баланса пользователя, проверка наличия активов,
        //  взаимодействие с историей операций; в каком формате лучше передавать исключения будет потом;
        //  Написать тесты

    }
}
