package com.sbrf.student.cryptoinvest.asset.service.impl;

import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.repository.OperationHistoryRepository;
import com.sbrf.student.cryptoinvest.asset.service.Impl.OperationHistoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тестирование {@link OperationHistoryServiceImpl}
 */
public class OperationHistoryServiceImplTest {

    @Mock
    private OperationHistoryRepository operationHistoryRepository;

    @InjectMocks
    private OperationHistoryServiceImpl operationHistoryService;

    /**
     * Инициализация и внедрение моков
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тестирование {@link OperationHistoryServiceImpl#getUserOperationHistory(Long)}.
     *
     * Проверяет, что история операций для заданного пользователя
     * корректно возвращается из репозитория.
     */
    @Test
    public void testGetUserOperationHistory_ReturnsOperations() {
        Long userId = 1L;

        /**
         * Создание тестовых данных
         */
        OperationHistory operation1 = new OperationHistory();
        operation1.setOperationId(1L);
        operation1.setCryptoId(100L);
        operation1.setSumOperation(BigDecimal.valueOf(1000));
        operation1.setPurchaseDate(LocalDateTime.now());

        OperationHistory operation2 = new OperationHistory();
        operation2.setOperationId(2L);
        operation2.setCryptoId(100L);
        operation2.setSumOperation(BigDecimal.valueOf(2000));
        operation2.setPurchaseDate(LocalDateTime.now().minusDays(1));

        List<OperationHistory> operations = Arrays.asList(operation1, operation2);

        /**
         * Настройка поведения mock объекта
         */
        when(operationHistoryRepository.findByAsset_UserIdOrderByPurchaseDateDesc(userId)).
                thenReturn(operations);

        /**
         * Вызов тестируемого метода
         */
        List<OperationHistory> result = operationHistoryService.getUserOperationHistory(userId);

        assertEquals(2, result.size());
        assertEquals(operation1, result.get(0)); // последняя операция
        assertEquals(operation2, result.get(1)); // предыдущая операция

        /**
         * Проверка, что метод репозитория был вызван
         */
        verify(operationHistoryRepository, times(1)).
                                                              findByAsset_UserIdOrderByPurchaseDateDesc(userId);
    }

    /**
     * Тестирование метода {@link OperationHistoryServiceImpl#getUserOperationHistory(Long)}
     * при отсутствии операций для указанного идентификатора пользователя.
     *
     * Проверяет, что возвращается пустой список.
     */
    @Test
    public void testGetUserOperationHistory_ReturnsEmptyList() {
        Long userId = 2L;

        /**
         * Настройка поведения mock объекта
         */
        when(operationHistoryRepository.findByAsset_UserIdOrderByPurchaseDateDesc(userId)).
                thenReturn(Collections.emptyList());

        /**
         * Вызов метода
         */
        List<OperationHistory> result = operationHistoryService.getUserOperationHistory(userId);

        /**
         * Проверка результатов
         */
        assertEquals(0, result.size());

        /**
         * Проверка, что метод репозитория был вызван
         */
        verify(operationHistoryRepository, times(1)).
                findByAsset_UserIdOrderByPurchaseDateDesc(userId);
    }

    /**
     * Тестирование метода {@link OperationHistoryServiceImpl#getUserOperationHistoryByCrypto(Long, Long)}.
     *
     * Проверяет, что история операций для заданного идентификатора пользователя и идентификатора криптовалюты
     * корректно возвращается из репозитория.
     */
    @Test
    public void testGetUserOperationHistoryByCrypto_ReturnsOperations() {
        Long userId = 1L;
        Long cryptoId = 100L;

        // Создание тестовых данных
        OperationHistory operation1 = new OperationHistory();
        operation1.setOperationId(1L);
        operation1.setCryptoId(cryptoId);
        operation1.setSumOperation(BigDecimal.valueOf(1000));
        operation1.setPurchaseDate(LocalDateTime.now());

        OperationHistory operation2 = new OperationHistory();
        operation2.setOperationId(2L);
        operation2.setCryptoId(cryptoId);
        operation2.setSumOperation(BigDecimal.valueOf(2000));
        operation2.setPurchaseDate(LocalDateTime.now().minusDays(1));

        List<OperationHistory> operations = Arrays.asList(operation1, operation2);

        // Настройка поведения mock объекта
        when(operationHistoryRepository.findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(userId,
                cryptoId)).thenReturn(operations);

        // Вызов метода
        List<OperationHistory> result = operationHistoryService.getUserOperationHistoryByCrypto(userId,
                cryptoId);

        // Проверка результатов
        assertEquals(2, result.size());
        assertEquals(operation1, result.get(0)); // последняя операция
        assertEquals(operation2, result.get(1)); // предыдущая операция

        // Проверка, что метод репозитория был вызван
        verify(operationHistoryRepository, times(1)).
                findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(userId, cryptoId);
    }

    /**
     * Тестирование метода {@link OperationHistoryServiceImpl#getUserOperationHistoryByCrypto(Long, Long)}
     * при отсутствии операций для указанного идентификатора пользователя и идентификатора криптовалюты.
     *
     * Проверяет, что возвращается пустой список.
     */
    @Test
    public void testGetUserOperationHistoryByCrypto_ReturnsEmptyList() {
        Long userId = 2L;
        Long cryptoId = 100L;

        // Настройка поведения mock объекта
        when(operationHistoryRepository.findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(userId,
                cryptoId)).thenReturn(Collections.emptyList());

        // Вызов метода
        List<OperationHistory> result = operationHistoryService.getUserOperationHistoryByCrypto(userId,
                cryptoId);

        // Проверка результатов
        assertEquals(0, result.size());

        // Проверка, что метод репозитория был вызван
        verify(operationHistoryRepository, times(1)).
                findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(userId, cryptoId);
    }
}