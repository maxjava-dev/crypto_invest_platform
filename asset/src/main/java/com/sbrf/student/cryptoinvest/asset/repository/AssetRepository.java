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
     * Получить список активов, принадлежащих конкретному пользователю
     * @param userId ID пользователя
     * @return список активов конкретного пользователя
     */
    List<Asset> findByUserId(Long userId);
}
