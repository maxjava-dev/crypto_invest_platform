package com.sbrf.student.cryptoinvest.asset.repository;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестирование {@link AssetRepository}
 */
@DataJpaTest
public class AssetRepositoryTest {
    @Autowired
    private AssetRepository assetRepository;

    private Asset asset1;
    private Asset asset2;

    /**
     * Очистка бд и создание тестовых данных
     */
    @BeforeEach
    public void setUp() {
        assetRepository.deleteAll();

        asset1 = new Asset();
        asset1.setUserId(1L);
        asset1.setCryptoId(100L);
        asset1.setQuantity(BigDecimal.valueOf(10.5));
        asset1.setCost(BigDecimal.valueOf(105));
        assetRepository.save(asset1);

        asset2 = new Asset();
        asset2.setUserId(1L);
        asset2.setCryptoId(101L);
        asset2.setQuantity(BigDecimal.valueOf(20.0));
        asset2.setCost(BigDecimal.valueOf(200));
        assetRepository.save(asset2);
    }

    /**
     * Тестирование метода {@link AssetRepository#findOwnedAssetsByUserId(Long)}.
     * Проверка нахождения активов у существующего пользователя
     */
    @Test
    public void testFindOwnedAssetsByUserId() {
        /**
         * Находит активы с указанным id пользователя
         */
        List<Asset> assets = assetRepository.findOwnedAssetsByUserId(1L);

        /**
         * Проверка что именно 2 актива(количество) у этого пользователя, согласно инициализации
         */
        assertThat(assets).hasSize(2);

        /**
         * Проверяет что содержатся конкретно эти активы, согласно инициализаци(порядок игнорируется)
         */
        assertThat(assets).containsExactlyInAnyOrder(asset1, asset2);
    }

    /**
     * Тестирование метода {@link AssetRepository#findOwnedAssetsByUserId(Long)}.
     * Проверка нахождения активов в случае обращения к несуществующему пользователю
     */
    @Test
    public void testFindOwnedAssetsByUserId_NoResults() {
        /**
         * Поиск активов у несуществующего пользователю
         *
         */
        List<Asset> assets = assetRepository.findOwnedAssetsByUserId(2L);

        /**
         * Проверяет, что возвращаемый список активов пуст
         */
        assertThat(assets).isEmpty();
    }

    /**
     * Тестирование метода {@link AssetRepository#findByUserIdAndCryptoId(Long, Long)}.
     * Проверка нахождения конкретного актива пользователя
     */
    @Test
    public void testFindByUserIdAndCryptoId_ReturnsCorrectAsset() {
        /**
         * Поиск конкретного актива по userId и cryptoId
         */
        Asset foundAsset = assetRepository.findByUserIdAndCryptoId(1L, 100L);

        /**
         * Проверка, что найденный актив не null и совпадает с ожидаемым активом
         */
        assertThat(foundAsset).isNotNull();
        assertThat(foundAsset).isEqualTo(asset1);
    }

    /**
     * Тестирование метода {@link AssetRepository#findByUserIdAndCryptoId(Long, Long)}.
     * Проверка случая, когда актив не найден
     */
    @Test
    public void testFindByUserIdAndCryptoId_NoResult() {
        /**
         * Поиск актива у пользователя, для которого нет данных
         */
        Asset foundAsset = assetRepository.findByUserIdAndCryptoId(1L, 999L);

        /**
         * Проверка, что актив не найден
         */
        assertThat(foundAsset).isNull();
    }
}
