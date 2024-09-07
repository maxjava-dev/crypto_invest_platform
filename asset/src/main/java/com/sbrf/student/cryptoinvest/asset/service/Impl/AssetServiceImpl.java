package com.sbrf.student.cryptoinvest.asset.service.Impl;

import com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice.CryptoServiceResponse;
import com.sbrf.student.cryptoinvest.asset.dto.AccountBalanceChangeDTO;
import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.model.OperationType;
import com.sbrf.student.cryptoinvest.asset.repository.AssetRepository;
import com.sbrf.student.cryptoinvest.asset.repository.OperationHistoryRepository;
import com.sbrf.student.cryptoinvest.asset.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Реализация {@link AssetService}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AssetServiceImpl implements AssetService {
    private final RestTemplate restTemplate;
    private final AssetRepository assetRepository;
    private final OperationHistoryRepository operationHistoryRepository;

    @Value("${CRYPTO_URL}")
    private String CRYPTOCURRENCY_SERVICE_URL;

    @Value("${USERS_URL}")
    private String USERS_SERVICE_UPDATE_BALANCE_URL;

    private final String USERS_SERVICE_UPDATE_BALANCE_SUFFIX = "/account/topup";

    /**
     * Получить список активов, принадлежащих конкретному пользователю
     * @param userId ID пользователя, для которого нужно получить список активов
     * @return Список активов, принадлежащих конкретному пользователю
     */
    @Override
    public List<Asset> getOwnedAssetsByUserId(Long userId) {
        log.info("Получение списка активов для пользователя с ID: {}", userId);
        List<Asset> assets = assetRepository.findOwnedAssetsByUserId(userId);
        log.info("Найдено {} активов для пользователя с ID: {}", assets.size(), userId);
        return assets;
    }


    @Override
    public void buyAsset(Long cryptoId, Long userId, BigDecimal quantity) {
        log.info("Попытка покупки актива с ID: {} пользователем с ID: {} в количестве: {}", cryptoId,
                                                                                          userId, quantity);

        CryptoServiceResponse response = fetchCryptoData(cryptoId);
        BigDecimal price = response.getPrice();
        BigDecimal totalCost = price.multiply(quantity);

        log.info("Цена за единицу актива: {}. Общая стоимость покупки: {}", price, totalCost);

        /**
         * Проверка и обновление баланса пользователя, уменьшая его на сумму покупки
         */
        updateUserBalance(userId, totalCost.negate());

        /**
         * Проверка существования актива для данного пользователя и криптовалюты(была ли уже операция с
         * этим активом)
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
            log.info("Создан новый актив: {}", asset);
        } else {
            /**
             * Если актив существует, происходит обновление количества актива
             */
            asset.setQuantity(asset.getQuantity().add(quantity));
            log.info("Актив уже существует. Обновлено его количество: {}", asset);
        }

        /**
         * Сохранение актива в базу данных
         */
        assetRepository.save(asset);
        log.info("Актив успешно сохранен в базе данных: {}", asset);

        /**
         * Сохранение транзакции в историю операций
         */
        saveOperationHistory(asset, OperationType.buy, quantity);
        log.info("Транзакция успешно сохранена в базу данных: {}, тип операции buy: {}, количество: {}",
                asset, OperationType.buy, quantity);
    }

    @Override
    public void sellAsset(Long cryptoId, Long userId, BigDecimal quantity) {
        log.info("Попытка продажи актива с ID: {} пользователем с ID: {} в количестве: {}", cryptoId, userId, quantity);
        CryptoServiceResponse response = fetchCryptoData(cryptoId);
        BigDecimal price = response.getPrice();
        BigDecimal totalRevenue = price.multiply(quantity);

        /**
         * Проверка наличия актива для продажи
         */
        Asset asset = assetRepository.findByUserIdAndCryptoId(userId, cryptoId);
        if (asset == null || asset.getQuantity().compareTo(quantity) < 0) {
            log.error("Недостаточно активов для продажи. Актив: {}, Запрашиваемое количество: {}", asset, quantity);
            throw new RuntimeException("Недостаточно активов для продажи.");
        }

        /**
         * Обновление количества актива
         */
        asset.setQuantity(asset.getQuantity().subtract(quantity));

        /**
         * Проверка и обновление баланса пользователя, увеличение его на сумму продажи
         */
        updateUserBalance(userId, totalRevenue);

        /**
         * Если количество после продажи равно нулю, не удаляем запись в бд, ставим количество 0
         */
        if (asset.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            asset.setQuantity(BigDecimal.ZERO);
            log.info("Количество актива после продажи стало нулевым, актив обновлен: {}", asset);
        }

        /**
         * Сохранение актива в базу данных
         */
        assetRepository.save(asset);
        log.info("Актив успешно сохранен в базе данных после продажи: {}", asset);

        /**
         * Сохранение транзакции в историю операций
         */
        saveOperationHistory(asset, OperationType.sell, quantity);
        log.info("Транзакция успешно сохранена в базу данных: {}, тип операции sell: {}, количество: {}",
        asset, OperationType.sell, quantity);
    }

    /**
     * Сохранение операции в историю транзакций
     * @param asset - актив пользователя
     * @param operationType - тип операции (покупка/продажа)
     * @param quantity - количество актива
     */
    private void saveOperationHistory(Asset asset, OperationType operationType, BigDecimal quantity) {
        /**
         * Получаем цену актива из сервиса CryptoCurrency
         */
        CryptoServiceResponse cryptoData = fetchCryptoData(asset.getCryptoId());

        /**
         * getPrice() возвращает цену за единицу
         */
        BigDecimal price = cryptoData.getPrice();

        /**
         * Расчет общей суммы операции
         */
        BigDecimal totalSum = price.multiply(quantity);

        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setAsset(asset);
        operationHistory.setCryptoId(asset.getCryptoId());
        operationHistory.setOperationType(operationType);
        operationHistory.setSumOperation(totalSum);
        operationHistory.setQuantityCurrentOperation(quantity);
        operationHistory.setPurchaseDate(LocalDateTime.now());
        operationHistory.setQuantity(asset.getQuantity());

        operationHistoryRepository.save(operationHistory);
        log.info("Операция сохранена в историю: тип = {}, актив = {}, количество = {}, сумма = {}",
                operationType, asset.getCryptoId(), quantity, totalSum);
    }

    /**
     * Вспомогательный метод для проверки и обновления баланса пользователя, обращение к сервису users
     * @param userId ID пользователя, для которого нужно обновить баланс
     * @param amount сумма операции
     */
    private void updateUserBalance(Long userId, BigDecimal amount) {
        /**
         * Преобразование в String для корректной передачи dto
         */
        String amountStr = amount.toPlainString();
        AccountBalanceChangeDTO balanceChangeDTO = new AccountBalanceChangeDTO(userId, amountStr);

        log.info("Обновление баланса для пользователя: {} на сумму: {}", userId, amountStr);

        try {
            restTemplate.put(USERS_SERVICE_UPDATE_BALANCE_URL + USERS_SERVICE_UPDATE_BALANCE_SUFFIX, balanceChangeDTO);
        } catch (RestClientException e) {
            log.error("Ошибка при обновлении баланса счета: {}", e.getMessage(), e);
            throw new RuntimeException("Ошибка при обновлении баланса счета", e);
        }
    }

    /**
     * Вспомогательный метод для получения криптовалюты по ID, из сервиса Cryptocurrency
     * @param cryptoId ID криптовалюты
     * @return информация о криптовалюте в формате json
     */
    private CryptoServiceResponse fetchCryptoData(Long cryptoId) {
        String url = CRYPTOCURRENCY_SERVICE_URL + "/crypto/" + cryptoId;
        try {
            return restTemplate.getForObject(url, CryptoServiceResponse.class);
        } catch (RestClientException e) {
            log.error("Не удалось получить данные о криптовалюте с ID: {}", cryptoId, e);
            throw new RuntimeException("Не удалось получить данные о криптовалюте", e);
        }
    }
}
