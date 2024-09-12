package com.sbrf.student.cryptoinvest.asset.tests;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.repository.AssetRepository;
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
        assetRepository.save(asset1);

        asset2 = new Asset();
        asset2.setUserId(1L);
        asset2.setCryptoId(101L);
        asset2.setQuantity(BigDecimal.valueOf(20.0));
        assetRepository.save(asset2);
    }

    /**
     * Тестирование метода {@link AssetRepository#findOwnedAssetsByUserId(Long)}.
     *
     * Проверка нахождения активов у существующего пользователя
     */
    @Test
    public void testFindByUserId() {
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
     *
     * Проверка нахождения активов в случае обращения к несуществующему пользователю
     */
    @Test
    public void testFindByUserId_NoResults() {
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
}
