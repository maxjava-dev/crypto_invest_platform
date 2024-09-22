package com.sbrf.student.cryptoinvest.asset.controllers;

import com.sbrf.student.cryptoinvest.asset.model.*;
import com.sbrf.student.cryptoinvest.asset.service.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Юнит тесты для контроллера {@link AssetController}
 */
public class AssetControllerTest {

    @Mock
    private AssetService assetService;

    @InjectMocks
    private AssetController assetController;

    /**
     * Инициализация моков перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест на проверку метода {@link AssetController#getAssets(Long)},
     * который должен вернуть список активов для пользователя.
     * Проверяется правильность возвращаемого списка и статус-код ответа.
     */
    @Test
    void getAssets_ReturnsAssetList() {
        Long userId = 1L;
        List<Asset> mockAssets = Collections.singletonList(new Asset());
        when(assetService.getOwnedAssetsByUserId(userId)).thenReturn(mockAssets);

        ResponseEntity<List<Asset>> response = assetController.getAssets(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAssets, response.getBody());
        verify(assetService, times(1)).getOwnedAssetsByUserId(userId);
    }

    /**
     * Тест на проверку метода {@link AssetController#getAssets(Long)},
     * который должен вернуть пустой список активов для пользователя.
     * Проверяется правильность возвращаемого списка и статус-код ответа.
     */
    @Test
    void getAssets_ReturnsEmptyAssetList() {
        Long userId = 1L;
        List<Asset> mockAssets = Collections.emptyList(); // Пустой список активов
        when(assetService.getOwnedAssetsByUserId(userId)).thenReturn(mockAssets);

        ResponseEntity<List<Asset>> response = assetController.getAssets(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockAssets, response.getBody());
        verify(assetService, times(1)).getOwnedAssetsByUserId(userId);
    }

    /**
     * Тест для метода {@link AssetController#handleAssetTransaction(Long, Long, BigDecimal, String)},
     * который обрабатывает покупку актива.
     * Проверяется корректность вызова метода на сервисе и возвращаемый статус-код.
     */
    @Test
    void handleAssetTransaction_BuyOperation_Success() throws Exception {
        Long cryptoId = 1L;
        Long userId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(10);
        String operationType = "buy";

        ResponseEntity<Void> response = assetController.handleAssetTransaction(cryptoId, userId, quantity, operationType);
        assertEquals(200, response.getStatusCodeValue());
        verify(assetService, times(1)).buyAsset(cryptoId, userId, quantity);
    }

    /**
     * Тест для метода {@link AssetController#handleAssetTransaction(Long, Long, BigDecimal, String)},
     * который обрабатывает продажу актива.
     * Проверяется корректность вызова метода на сервисе и возвращаемый статус-код.
     */
    @Test
    void handleAssetTransaction_SellOperation_Success() throws Exception {
        Long cryptoId = 1L;
        Long userId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(5);
        String operationType = "sell";

        ResponseEntity<Void> response = assetController.handleAssetTransaction(cryptoId, userId, quantity, operationType);
        assertEquals(200, response.getStatusCodeValue());
        verify(assetService, times(1)).sellAsset(cryptoId, userId, quantity);
    }

    /**
     * Тест на проверку выброса исключения при передаче null в качестве типа операции в метод
     * {@link AssetController#handleAssetTransaction(Long, Long, BigDecimal, String)}.
     * Проверяется, что выбрасывается корректное сообщение об ошибке.
     */
    @Test
    void handleAssetTransaction_InvalidOperationNull_ThrowsException() {
        Long cryptoId = 1L;
        Long userId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(5);
        String invalidOperationType = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                assetController.handleAssetTransaction(cryptoId, userId, quantity, invalidOperationType));

        assertEquals("Неверный тип операции: " + invalidOperationType, exception.getMessage());
    }

    /**
     * Тест на проверку выброса исключения при передаче невалидного типа операции в метод
     * {@link AssetController#handleAssetTransaction(Long, Long, BigDecimal, String)}.
     * Проверяется, что выбрасывается корректное сообщение об ошибке.
     */
    @Test
    void handleAssetTransaction_InvalidOperationType_ThrowsException() {
        Long cryptoId = 1L;
        Long userId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(5);
        String invalidOperationType = "invalid"; // Неправильное значение

        // Проверяем, что при передаче неправильного значения выбрасывается исключение
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                assetController.handleAssetTransaction(cryptoId, userId, quantity, invalidOperationType));

        // Проверяем, что сообщение об ошибке соответствует ожидаемому
        assertEquals("Неверный тип операции: " + invalidOperationType, exception.getMessage());
    }
}
