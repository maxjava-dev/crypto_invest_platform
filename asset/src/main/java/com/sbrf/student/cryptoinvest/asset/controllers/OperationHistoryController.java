package com.sbrf.student.cryptoinvest.asset.controllers;

import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.service.OperationHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Контроллер для работы с историей операций активов
 */
@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
@Slf4j
public class OperationHistoryController {

    private final OperationHistoryService operationHistoryService;

    /**
     * Получить весь список операций пользователя по всем активам
     *
     * @param userId ID пользователя
     * @return Список всех транзакций пользователя
     */
    @GetMapping("/user/{userId}")
    public List<OperationHistory> getUserOperations(@PathVariable Long userId) {
        log.info("Получение списка операций пользователя {}", userId);
        List<OperationHistory> operations = operationHistoryService.getUserOperationHistory(userId);
        log.info("Найдено {} операций для пользователя с ID: {}", operations.size(), userId);
        return operations;
    }

    /**
     * Получить список операций пользователя по конкретной криптовалюте
     *
     * @param userId ID пользователя
     * @param cryptoId ID криптовалюты
     * @return Список транзакций по конкретной криптовалюте
     */
    @GetMapping("/user/{userId}/crypto/{cryptoId}")
    public List<OperationHistory> getUserOperationsByCrypto(@PathVariable Long userId, @PathVariable Long cryptoId) {
        log.info("Получение транзакций пользователя с ID: {} по криптовалюте с ID: {}", userId, cryptoId);
        List<OperationHistory> operations = operationHistoryService.getUserOperationHistoryByCrypto(userId, cryptoId);
        log.info("Найдено {} транзакций для пользователя с ID: {} по криптовалюте с ID: {}", operations.size(), userId, cryptoId);
        return operations;
    }
}
