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
     * Получить историю операций по всем активам пользователя
     *
     * @param userId ID пользователя
     * @return Список операций по всем активам у пользователя
     */
    List<OperationHistory> findByAsset_UserIdOrderByPurchaseDateDesc(Long userId);

    /**
     * Получить историю операций по конкретной криптовалюте
     *
     * @param userId ID пользователя
     * @param cryptoId ID криптовалюты
     * @return Список операций по конкретной криптовалюте
     */
    List<OperationHistory> findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(Long userId, Long cryptoId);
}
