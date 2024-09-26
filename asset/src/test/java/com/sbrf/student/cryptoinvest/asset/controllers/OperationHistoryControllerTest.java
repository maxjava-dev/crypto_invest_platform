package com.sbrf.student.cryptoinvest.asset.controllers;

import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.service.OperationHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Юнит тесты для контроллера Истории операций {@link OperationHistoryController}
 */
public class OperationHistoryControllerTest {

    @Mock
    private OperationHistoryService operationHistoryService;

    @InjectMocks
    private OperationHistoryController operationHistoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест на проверку метода {@link OperationHistoryController#getUserOperations(Long)},
     * который должен вернуть список всех операций пользователя.
     */
    @Test
    void getUserOperations_ReturnsUserOperationHistory() {
        Long userId = 1L;
        List<OperationHistory> mockOperations = Collections.singletonList(new OperationHistory());
        when(operationHistoryService.getUserOperationHistory(userId)).thenReturn(mockOperations);

        List<OperationHistory> result = operationHistoryController.getUserOperations(userId);

        assertEquals(mockOperations, result);
        verify(operationHistoryService, times(1)).getUserOperationHistory(userId);
    }

    /**
     * Тест на проверку метода {@link OperationHistoryController#getUserOperations(Long)},
     * который должен вернуть пустой список операций для пользователя.
     */
    @Test
    void getUserOperations_ReturnsEmptyUserOperationHistory() {
        Long userId = 1L;
        List<OperationHistory> mockOperations = Collections.emptyList();
        when(operationHistoryService.getUserOperationHistory(userId)).thenReturn(mockOperations);

        List<OperationHistory> result = operationHistoryController.getUserOperations(userId);

        assertEquals(mockOperations, result);
        verify(operationHistoryService, times(1)).getUserOperationHistory(userId);
    }

    /**
     * Тест на проверку метода {@link OperationHistoryController#getUserOperationsByCrypto(Long, Long)},
     * который должен вернуть список операций пользователя по конкретной криптовалюте.
     */
    @Test
    void getUserOperationsByCrypto_ReturnsUserOperationHistoryByCrypto() {
        Long userId = 1L;
        Long cryptoId = 1L;
        List<OperationHistory> mockOperations = Collections.singletonList(new OperationHistory());
        when(operationHistoryService.getUserOperationHistoryByCrypto(userId, cryptoId)).thenReturn(mockOperations);

        List<OperationHistory> result = operationHistoryController.getUserOperationsByCrypto(userId, cryptoId);

        assertEquals(mockOperations, result);
        verify(operationHistoryService, times(1)).getUserOperationHistoryByCrypto(userId, cryptoId);
    }

    /**
     * Тест на проверку метода {@link OperationHistoryController#getUserOperationsByCrypto(Long, Long)},
     * который должен вернуть пустой список операций по конкретной криптовалюте.
     */
    @Test
    void getUserOperationsByCrypto_ReturnsEmptyUserOperationHistoryByCrypto() {
        Long userId = 1L;
        Long cryptoId = 1L;
        List<OperationHistory> mockOperations = Collections.emptyList();
        when(operationHistoryService.getUserOperationHistoryByCrypto(userId, cryptoId)).thenReturn(mockOperations);

        List<OperationHistory> result = operationHistoryController.getUserOperationsByCrypto(userId, cryptoId);

        assertEquals(mockOperations, result);
        verify(operationHistoryService, times(1)).getUserOperationHistoryByCrypto(userId, cryptoId);
    }
}
