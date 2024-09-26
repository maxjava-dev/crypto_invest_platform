package com.sbrf.student.cryptoinvest.asset.service;

import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;

import java.util.List;

/**
 * Сервис для работы с историей операций пользователя
 */
public interface OperationHistoryService {

    /**
     * Получить историю операций пользователя по всем активам
     *
     * @param userId ID пользователя
     * @return Список всех операций пользователя
     */
    List<OperationHistory> getUserOperationHistory(Long userId);

    /**
     * Получить историю операций по конкретной криптовалюте
     *
     * @param userId ID пользователя
     * @param cryptoId ID криптовалюты
     * @return Список операций по конкретной криптовалюте
     */
    List<OperationHistory> getUserOperationHistoryByCrypto(Long userId, Long cryptoId);
}
