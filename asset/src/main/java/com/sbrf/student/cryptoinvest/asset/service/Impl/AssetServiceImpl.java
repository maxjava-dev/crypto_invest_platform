package com.sbrf.student.cryptoinvest.asset.service.Impl;

import com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice.CryptoServiceResponse;
import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.model.OperationType;
import com.sbrf.student.cryptoinvest.asset.repository.AssetRepository;
import com.sbrf.student.cryptoinvest.asset.service.AssetService;
import com.sbrf.student.cryptoinvest.asset.service.api.cryptocurrency.Impl.CryptoCurrencyServiceImpl;
import com.sbrf.student.cryptoinvest.asset.service.api.userService.Impl.BalanceManagerImpl;
import com.sbrf.student.cryptoinvest.asset.service.manager.OperationHistoryManager;
import com.sbrf.student.cryptoinvest.asset.util.AssetCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация {@link AssetService}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;
    private final CryptoCurrencyServiceImpl cryptoCurrencyService;
    private final BalanceManagerImpl balanceManager;
    private final OperationHistoryManager operationHistoryManager;

    /**
     * Получить список активов, принадлежащих конкретному пользователю
     *
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
        /**
         * Получение данных по криптовалюте
         */
        CryptoServiceResponse response = cryptoCurrencyService.fetchCryptoData(cryptoId);
        BigDecimal price = response.getPrice();
        BigDecimal totalCost = price.multiply(quantity);

        log.info("Цена за единицу актива: {}. Общая стоимость покупки: {}", price, totalCost);

        /**
         * Проверка и обновление баланса пользователя, уменьшая его на сумму покупки
         */
        balanceManager.updateUserBalance(userId, totalCost.negate(), BigDecimal.ZERO);

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
            asset.setCost(totalCost);
            log.info("Создан новый актив: {}", asset);
        } else {
            /**
             * Если актив существует, происходит обновление количества и стоимости актива
             */
            asset.setQuantity(asset.getQuantity().add(quantity));
            asset.setCost(asset.getCost().add(totalCost));
            log.info("Актив уже существует. Обновлено его количество и стоимость: {}", asset);
        }

        /**
         * Сохранение актива в базу данных
         */
        assetRepository.save(asset);
        log.info("Актив успешно сохранен в базе данных: {}", asset);

        /**
         * Сохранение транзакции в историю операций
         */
        operationHistoryManager.saveOperationHistory(asset, OperationType.buy, quantity, totalCost, BigDecimal.ZERO);
        log.info("Транзакция успешно сохранена в базу данных: {}, тип операции buy: {}, количество: {}, сумма {}",
                asset, OperationType.buy, quantity, totalCost);
    }

    @Override
    public void sellAsset(Long cryptoId, Long userId, BigDecimal quantity) {
        log.info("Попытка продажи актива с ID: {} пользователем с ID: {} в количестве: {}", cryptoId, userId, quantity);
        CryptoServiceResponse response = cryptoCurrencyService.fetchCryptoData(cryptoId);
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
         * Обновление количества и стоимости актива
         */
        BigDecimal newQuantity = asset.getQuantity().subtract(quantity);
        BigDecimal newCost = AssetCalculator.calculateNewCost(asset.getCost(), asset.getQuantity(), newQuantity);
        BigDecimal income = AssetCalculator.calculateIncome(totalRevenue, asset.getCost(), newCost);
        asset.setQuantity(newQuantity);
        asset.setCost(newCost);

        /**
         * Проверка и обновление баланса пользователя, увеличение его на сумму продажи
         */
        balanceManager.updateUserBalance(userId, totalRevenue, income);

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
        operationHistoryManager.saveOperationHistory(asset, OperationType.sell, quantity, totalRevenue, income);
        log.info("Транзакция успешно сохранена в базу данных: {}, тип операции sell: {}, количество: {}",
        asset, OperationType.sell, quantity);
    }
}
