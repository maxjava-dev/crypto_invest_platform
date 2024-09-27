package com.sbrf.student.cryptoinvest.asset.service.impl;

import com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice.CryptoServiceResponse;
import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.repository.AssetRepository;
import com.sbrf.student.cryptoinvest.asset.service.Impl.AssetServiceImpl;
import com.sbrf.student.cryptoinvest.asset.service.api.cryptocurrency.Impl.CryptoCurrencyServiceImpl;
import com.sbrf.student.cryptoinvest.asset.service.manager.OperationHistoryManager;
import com.sbrf.student.cryptoinvest.asset.service.api.userService.Impl.BalanceManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса {@link AssetServiceImpl}
 */
public class AssetServiceImplTest {

    @Mock
    private AssetRepository assetRepository;

    @Mock
    private CryptoCurrencyServiceImpl cryptoCurrencyService;

    @Mock
    private BalanceManagerImpl balanceManager;

    @Mock
    private OperationHistoryManager operationHistoryManager;

    @InjectMocks
    private AssetServiceImpl assetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     *  Тестирование метода {@link AssetServiceImpl#getOwnedAssetsByUserId(Long)}.
     *  Проверка что возвращаемый список активов соответствует ожиданиям.
     */
    @Test
    public void testGetOwnedAssetsByUserId() {
        Long userId = 1L;
        List<Asset> assets = new ArrayList<>();
        assets.add(new Asset()); // добавляем пример актива
        when(assetRepository.findOwnedAssetsByUserId(userId)).thenReturn(assets);

        List<Asset> result = assetService.getOwnedAssetsByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assetRepository, times(1)).findOwnedAssetsByUserId(userId);
    }

    /**
     *  Тестирование метода {@link AssetServiceImpl#buyAsset(Long, Long, BigDecimal)}.
     *  Проверяет сценарий покупки нового актива, который еще не существует в базе данных.
     */
    @Test
    public void testBuyAsset_NewAsset() {
        Long cryptoId = 1L;
        Long userId = 1L;
        BigDecimal quantity = BigDecimal.valueOf(5);
        BigDecimal price = BigDecimal.valueOf(100);

        CryptoServiceResponse response = new CryptoServiceResponse();
        response.setPrice(price);

        when(cryptoCurrencyService.fetchCryptoData(cryptoId)).thenReturn(response);
        when(assetRepository.findByUserIdAndCryptoId(userId, cryptoId)).thenReturn(null);

        assetService.buyAsset(cryptoId, userId, quantity);

        ArgumentCaptor<Asset> assetCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(assetRepository).save(assetCaptor.capture());

        Asset savedAsset = assetCaptor.getValue();
        assertEquals(userId, savedAsset.getUserId());
        assertEquals(cryptoId, savedAsset.getCryptoId());
        assertEquals(quantity, savedAsset.getQuantity());
        assertEquals(price.multiply(quantity), savedAsset.getCost());
    }

    /**
     * Тестирование метода {@link AssetServiceImpl#buyAsset(Long, Long, BigDecimal)}.
     * Проверяет сценарий покупки существующего актива и корректного обновления его количества и стоимости.
     */
    @Test
    public void testBuyAsset_ExistingAsset() {
        Long cryptoId = 1L;
        Long userId = 1L;
        BigDecimal quantityToBuy = BigDecimal.valueOf(5);
        BigDecimal price = BigDecimal.valueOf(100);

        CryptoServiceResponse response = new CryptoServiceResponse();
        response.setPrice(price);

        Asset existingAsset = new Asset();
        existingAsset.setUserId(userId);
        existingAsset.setCryptoId(cryptoId);
        existingAsset.setQuantity(BigDecimal.valueOf(2));
        existingAsset.setCost(price.multiply(BigDecimal.valueOf(2)));

        when(cryptoCurrencyService.fetchCryptoData(cryptoId)).thenReturn(response);
        when(assetRepository.findByUserIdAndCryptoId(userId, cryptoId)).thenReturn(existingAsset);

        assetService.buyAsset(cryptoId, userId, quantityToBuy);

        ArgumentCaptor<Asset> assetCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(assetRepository).save(assetCaptor.capture());

        Asset savedAsset = assetCaptor.getValue();
        assertEquals(userId, savedAsset.getUserId());
        assertEquals(cryptoId, savedAsset.getCryptoId());
        assertEquals(BigDecimal.valueOf(7), savedAsset.getQuantity()); // 2 + 5
        assertEquals(price.multiply(BigDecimal.valueOf(7)), savedAsset.getCost()); // (2 * price + 5 * price)
    }

    /**
     * Тестирование метода {@link AssetServiceImpl#sellAsset(Long, Long, BigDecimal)}.
     * Проверяет, что продажа актива успешно завершена и количество активов обновляется корректно.
     */
    @Test
    public void testSellAsset_Success() {
        Long cryptoId = 1L;
        Long userId = 1L;
        BigDecimal quantityToSell = BigDecimal.valueOf(2);
        BigDecimal price = BigDecimal.valueOf(100);

        CryptoServiceResponse response = new CryptoServiceResponse();
        response.setPrice(price);

        Asset existingAsset = new Asset();
        existingAsset.setUserId(userId);
        existingAsset.setCryptoId(cryptoId);
        existingAsset.setQuantity(BigDecimal.valueOf(5));
        existingAsset.setCost(price.multiply(BigDecimal.valueOf(5)));

        when(cryptoCurrencyService.fetchCryptoData(cryptoId)).thenReturn(response);
        when(assetRepository.findByUserIdAndCryptoId(userId, cryptoId)).thenReturn(existingAsset);

        assetService.sellAsset(cryptoId, userId, quantityToSell);

        ArgumentCaptor<Asset> assetCaptor = ArgumentCaptor.forClass(Asset.class);
        verify(assetRepository).save(assetCaptor.capture());

        Asset savedAsset = assetCaptor.getValue();
        assertEquals(userId, savedAsset.getUserId());
        assertEquals(cryptoId, savedAsset.getCryptoId());
        assertEquals(BigDecimal.valueOf(3), savedAsset.getQuantity()); // 5 - 2
    }

    /**
     * Тестирование метода {@link AssetServiceImpl#sellAsset(Long, Long, BigDecimal)}.
     * Проверяет, что выбрасывается исключение, когда пользователь пытается продать больше активов,
     * чем у него есть.
     */
    @Test
    public void testSellAsset_InsufficientQuantity() {
        Long cryptoId = 1L;
        Long userId = 1L;
        BigDecimal quantityToSell = BigDecimal.valueOf(10);

        Asset existingAsset = new Asset();
        existingAsset.setUserId(userId);
        existingAsset.setCryptoId(cryptoId);
        existingAsset.setQuantity(BigDecimal.valueOf(5));

        when(assetRepository.findByUserIdAndCryptoId(userId, cryptoId)).thenReturn(existingAsset);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            assetService.sellAsset(cryptoId, userId, quantityToSell);
        });

        String expectedMessage = "Недостаточно активов для продажи.";
        String actualMessage = exception.getMessage();

        assertFalse(actualMessage.contains(expectedMessage));
    }
}
