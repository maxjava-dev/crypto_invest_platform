package com.sbrf.student.cryptoinvest.asset.service.Impl;

import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.repository.OperationHistoryRepository;
import com.sbrf.student.cryptoinvest.asset.service.OperationHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Реализация {@link OperationHistoryService}
 */
@Service
@Slf4j
public class OperationHistoryServiceImpl implements OperationHistoryService {

    @Autowired
    private OperationHistoryRepository operationHistoryRepository;

    /**
     * Получить историю операций пользователя по всем активам
     *
     * @param userId ID пользователя
     * @return Список всех операций пользователя
     */
    public List<OperationHistory> getUserOperationHistory(Long userId) {
        log.info("Запрос на получение всех операций для пользователя с ID: {}", userId);

        List<OperationHistory> operations = operationHistoryRepository.findByAsset_UserIdOrderByPurchaseDateDesc(userId);
        log.info("Найдено {} операций для пользователя с ID: {}", operations.size(), userId);

        return operations;
    }

    /**
     * Получить историю операций по конкретной криптовалюте
     *
     * @param userId ID пользователя
     * @param cryptoId ID криптовалюты
     * @return Список операций по конкретной криптовалюте
     */
    public List<OperationHistory> getUserOperationHistoryByCrypto(Long userId, Long cryptoId) {
        log.info("Запрос на получение операций для пользователя с ID: {} по криптовалюте с ID: {}", userId, cryptoId);

        List<OperationHistory> operations = operationHistoryRepository.findByAsset_UserIdAndAsset_CryptoIdOrderByPurchaseDateDesc(userId, cryptoId);
        log.info("Найдено {} операций для пользователя с ID: {} по криптовалюте с ID: {}", operations.size(), userId, cryptoId);

        return operations;
    }
}

