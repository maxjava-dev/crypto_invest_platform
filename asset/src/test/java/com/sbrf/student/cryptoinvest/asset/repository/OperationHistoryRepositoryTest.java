package com.sbrf.student.cryptoinvest.asset.repository;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.model.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование {@link OperationHistoryRepository}
 */
@DataJpaTest
public class OperationHistoryRepositoryTest {

    @Autowired
    private OperationHistoryRepository operationHistoryRepository;

    @Autowired
    private AssetRepository assetRepository;

    private OperationHistory operation1;
    private OperationHistory operation2;
    private OperationHistory operation3;

    /**
     * Очистка бд и создание тестовых данных
     */
    @BeforeEach
    public void setUp() {
        // Удаляем старые данные
        assetRepository.deleteAll();
        operationHistoryRepository.deleteAll();

        // Создание актива
        Asset asset = new Asset();
        asset.setUserId(1L);
        asset.setCryptoId(100L);
        asset.setQuantity(BigDecimal.valueOf(10.5));
        asset.setCost(BigDecimal.valueOf(105));
        asset = assetRepository.save(asset);

        // Создание истории операций
        operation1 = new OperationHistory();
        operation1.setAsset(asset);
        operation1.setCryptoId(asset.getCryptoId());
        operation1.setOperationType(OperationType.buy);
        operation1.setSumOperation(BigDecimal.valueOf(1050));
        operation1.setQuantityCurrentOperation(BigDecimal.valueOf(10));
        operation1.setIncomeCurrentOperation(BigDecimal.ZERO);
        operation1.setPurchaseDate(LocalDateTime.now());
        operation1.setQuantity(BigDecimal.valueOf(10)); // Set the quantity here
        operationHistoryRepository.save(operation1);

        // Добавляем еще одну операцию
        operation2 = new OperationHistory();
        operation2.setAsset(asset);
        operation2.setCryptoId(asset.getCryptoId());
        operation2.setOperationType(OperationType.sell);
        operation2.setSumOperation(BigDecimal.valueOf(500));
        operation2.setQuantityCurrentOperation(BigDecimal.valueOf(5));
        operation2.setIncomeCurrentOperation(BigDecimal.valueOf(50));
        operation2.setPurchaseDate(LocalDateTime.now().minusDays(1)); // более ранняя дата
        operation2.setQuantity(BigDecimal.valueOf(5)); // Set the quantity here
        operationHistoryRepository.save(operation2);

        // Добавляем еще одну операцию
        operation3 = new OperationHistory();
        operation3.setAsset(asset);
        operation3.setCryptoId(asset.getCryptoId());
        operation3.setOperationType(OperationType.buy);
        operation3.setSumOperation(BigDecimal.valueOf(1000));
        operation3.setQuantityCurrentOperation(BigDecimal.valueOf(1));
        operation3.setIncomeCurrentOperation(BigDecimal.ZERO);
        operation3.setPurchaseDate(LocalDateTime.now().minusDays(2)); // более ранняя дата
        operation3.setQuantity(BigDecimal.valueOf(1)); // Set the quantity here
        operationHistoryRepository.save(operation3);
    }

    /**
     * Тестирование метода {@link OperationHistoryRepository#findByAsset_UserIdOrderByPurchaseDateDesc(Long)}.
     * Проверка нахождения операций пользователя по всем активам
     */
    @Test
    public void testFindByAsset_UserIdOrderByPurchaseDateDesc() {
        List<OperationHistory> operations = operationHistoryRepository.findByAsset_UserIdOrderByPurchaseDateDesc(1L);

        assertThat(operations).hasSize(3);
        assertThat(operations).containsExactly(operation1, operation2, operation3);
    }

    /**
     * Тестирование метода {@link OperationHistoryRepository#findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(Long, Long)}.
     * Проверка нахождения операций пользователя по конкретной криптовалюте
     */
    @Test
    public void testFindByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc() {
        List<OperationHistory> operations = operationHistoryRepository.findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(1L, 100L);

        assertThat(operations).hasSize(3);
        assertThat(operations).containsExactly(operation1, operation2, operation3);
    }

    /**
     * Тестирование метода {@link OperationHistoryRepository#findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(Long, Long)}.
     * Проверка нахождения операций по несуществующей криптовалюте
     */
    @Test
    public void testFindByAsset_UserIdAndAsset_CryptoId_NoResults() {
        List<OperationHistory> operations = operationHistoryRepository.findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(1L, 999L);

        assertThat(operations).isEmpty();
    }
}
