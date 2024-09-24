package com.sbrf.student.cryptoinvest.asset.service.impl;

import com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice.CryptoServiceResponse;
import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.repository.AssetRepository;
import com.sbrf.student.cryptoinvest.asset.repository.OperationHistoryRepository;
import com.sbrf.student.cryptoinvest.asset.service.Impl.AssetServiceImpl;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Юнит тесты для класса {@link AssetServiceImpl}
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AssetServiceImplTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private OperationHistoryRepository operationHistoryRepository;
    @InjectMocks
    private AssetServiceImpl assetService;

    @Before
    public void setUp() {
        assetService = new AssetServiceImpl(restTemplate, assetRepository, operationHistoryRepository);
    }

    /**
     * Тестирование успешной покупки актива, если актив для пользователя отсутствует.
     */
    @Test
    public void testBuyAsset_newAsset() {
        Long userId = 1L;
        Long cryptoId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal price = BigDecimal.valueOf(100);
        mockCryptoServiceResponse(price);
        when(assetRepository.findByUserIdAndCryptoId(userId, cryptoId)).thenReturn(null);

        /**
         * Проверка обновление баланса пользователя
         */
        doNothing().when(restTemplate).put(anyString(), any());
        assetService.buyAsset(cryptoId, userId, quantity);

        /**
         * Проверка вызова сохранения актива
         */
        verify(assetRepository, times(1)).save(any(Asset.class));

        /**
         * Проверка, что обновление баланса было вызвано
         */
        verify(restTemplate, times(1)).put(anyString(), any());
    }

    /**
     * Тестирование успешной покупки актива, если актив уже существует.
     */
    @Test
    public void testBuyAsset_existingAsset() {
        Long userId = 1L;
        Long cryptoId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(2);
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal existingQuantity = BigDecimal.valueOf(5);
        BigDecimal existingCost = BigDecimal.valueOf(500);
        mockCryptoServiceResponse(price);
        Asset existingAsset = createExistingAsset(userId, cryptoId, existingQuantity, existingCost);

        /**
         * Обновление баланса пользователя
         */
        doNothing().when(restTemplate).put(anyString(), any());
        assetService.buyAsset(cryptoId, userId, quantity);

        /**
         * Проверка вызова сохранения актива
         */
        verify(assetRepository, times(1)).save(any(Asset.class));

        /**
         * Проверка обновления количества актива
         */
        assertEquals(existingAsset.getQuantity(), existingQuantity.add(quantity));

        /**
         * Проверка, что обновление баланса было вызвано
         */
        verify(restTemplate, times(1)).put(anyString(), any());
    }

    /**
     * Тестирование успешной продажи актива.
     */
    @Test
    public void testSellAsset_successfulSale() {
        Long userId = 1L;
        Long cryptoId = 1L;
        BigDecimal quantityToSell = BigDecimal.valueOf(2);
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal existingQuantity = BigDecimal.valueOf(5);
        BigDecimal existingCost = BigDecimal.valueOf(500);
        mockCryptoServiceResponse(price);
        Asset existingAsset = createExistingAsset(userId, cryptoId, existingQuantity, existingCost);

        /**
         * Обновление баланса пользователя
          */
        doNothing().when(restTemplate).put(anyString(), any());
        assetService.sellAsset(cryptoId, userId, quantityToSell);

        /**
         * Проверка вызова сохранения актива
         */
        verify(assetRepository, times(1)).save(existingAsset);

        /**
         * Проверка обновления количества актива
         */
        assertEquals(existingAsset.getQuantity(), existingQuantity.subtract(quantityToSell));

        /**
         * Проверка, что обновление баланса было вызвано
         */
        verify(restTemplate, times(1)).put(anyString(), any());
    }

    /**
     * Тестирование попытки продать актив при недостаточном количестве актива.
     */
    @Test(expected = RuntimeException.class)
    public void testSellAsset_insufficientAssets() {
        Long userId = 1L;
        Long cryptoId = 1L;

        /**
         * Больше чем пользователь имеет
         */
        BigDecimal quantityToSell = BigDecimal.valueOf(10);
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal existingQuantity = BigDecimal.valueOf(5);
        BigDecimal existingCost = BigDecimal.valueOf(500);

        mockCryptoServiceResponse(price);
        createExistingAsset(userId, cryptoId, existingQuantity, existingCost);

        /**
         * Должно произойти исключение
         */
        assetService.sellAsset(cryptoId, userId, quantityToSell);

        /**
         * Проверка, что сохранение актива не было вызвано
         */
        verify(assetRepository, times(0)).save(any(Asset.class));

        /**
         * Проверка, что обновление баланса не было вызвано
         */
        verify(restTemplate, times(0)).put(anyString(), any());
    }

    /**
     * Вспомогательный метод для получения мока данных криптовалюты.
     */
    private void mockCryptoServiceResponse(BigDecimal price) {
        CryptoServiceResponse cryptoResponse = new CryptoServiceResponse();
        cryptoResponse.setPrice(price);
        when(restTemplate.getForObject(anyString(), eq(CryptoServiceResponse.class))).thenReturn(cryptoResponse);
    }

    /**
     * Вспомогательный метод для создания существующего актива.
     */
    private Asset createExistingAsset(Long userId, Long cryptoId, BigDecimal existingQuantity, BigDecimal existingCost) {
        Asset existingAsset = new Asset();
        existingAsset.setUserId(userId);
        existingAsset.setCryptoId(cryptoId);
        existingAsset.setQuantity(existingQuantity);
        existingAsset.setCost(existingCost);
        when(assetRepository.findByUserIdAndCryptoId(userId, cryptoId)).thenReturn(existingAsset);
        return existingAsset;
    }
}