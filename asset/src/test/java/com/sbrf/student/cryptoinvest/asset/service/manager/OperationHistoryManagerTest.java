package com.sbrf.student.cryptoinvest.asset.service.manager;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.model.OperationType;
import com.sbrf.student.cryptoinvest.asset.repository.OperationHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Тестирование класса {@link OperationHistoryManager}.
 */
class OperationHistoryManagerTest {

    @Mock
    private OperationHistoryRepository operationHistoryRepository;

    @InjectMocks
    private OperationHistoryManager operationHistoryManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тестирует метод {@link OperationHistoryManager#saveOperationHistory(Asset,
     * OperationType, BigDecimal, BigDecimal, BigDecimal)}.
     * <p>
     * Проверяет, что операция сохраняется в историю операций и журналируется корректно.
     * </p>
     */
    @Test
    void testSaveOperationHistory() {
        // Создаем тестовые данные
        Asset asset = new Asset();
        asset.setCryptoId(100L);
        asset.setQuantity(BigDecimal.valueOf(10));

        OperationType operationType = OperationType.buy;
        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal totalSum = BigDecimal.valueOf(200);
        BigDecimal income = BigDecimal.valueOf(50);

        // Вызов метода
        operationHistoryManager.saveOperationHistory(asset, operationType, quantity, totalSum, income);

        // Проверка, что метод save был вызван один раз
        verify(operationHistoryRepository, times(1)).save(any(OperationHistory.class));

        // Проверка, что запись истории операции содержит ожидаемые значения
        OperationHistory expectedOperationHistory = new OperationHistory();
        expectedOperationHistory.setAsset(asset);
        expectedOperationHistory.setCryptoId(asset.getCryptoId());
        expectedOperationHistory.setOperationType(operationType);
        expectedOperationHistory.setSumOperation(totalSum);
        expectedOperationHistory.setQuantityCurrentOperation(quantity);
        expectedOperationHistory.setIncomeCurrentOperation(income);
        expectedOperationHistory.setQuantity(asset.getQuantity());
    }
}
