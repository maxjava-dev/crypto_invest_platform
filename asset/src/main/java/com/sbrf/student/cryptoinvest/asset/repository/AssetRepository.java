package com.sbrf.student.cryptoinvest.asset.repository;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с активами
 */
@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    /**
     * Получить список активов пользователя, которыми он в данный момент владеет
     * @param userId ID пользователя
     * @return список активов пользователя, которыми он в данный момент владеет
     */
    List<Asset> findOwnedAssetsByUserId(Long userId);

    /**
     * Нахождение конкретного актива принадлежащего пользователю
     * @param userId ID пользователя
     * @param cryptoId ID актива
     * @return актив принадлежащий пользователю
     */
    Asset findByUserIdAndCryptoId(Long userId, Long cryptoId);

    // TODO: Получить список(историю) операций по конкретному пользователю
}
