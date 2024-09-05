package com.sbrf.student.cryptoinvest.asset.repository;

import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с историей операций пользователя
 */
@Repository
public interface OperationHistoryRepository extends JpaRepository<OperationHistory, Long> {
    /**
     * Получить историю операций по конкретному активу
     * @param assetId ID актива
     * @return Список операций по конкретному активу у конкретного пользователя
     */
    List<OperationHistory> findByAsset_AssetId(Long assetId);
}
