package com.sbrf.student.cryptoinvest.asset.service.manager;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.model.OperationHistory;
import com.sbrf.student.cryptoinvest.asset.model.OperationType;
import com.sbrf.student.cryptoinvest.asset.repository.OperationHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Менеджер для работы с историей операций пользователя
 */
@Service
@Slf4j
public class OperationHistoryManager {

    private final OperationHistoryRepository operationHistoryRepository;

    @Autowired
    public OperationHistoryManager(OperationHistoryRepository operationHistoryRepository) {
        this.operationHistoryRepository = operationHistoryRepository;
    }

    /**
     * Сохранение операции в историю транзакций
     *
     * @param asset         - актив пользователя
     * @param operationType - тип операции (покупка/продажа)
     * @param quantity      - количество актива
     * @param totalSum      - стоимость операции
     * @param income        - прибыль или убыток от операции
     */
    public void saveOperationHistory(Asset asset, OperationType operationType, BigDecimal quantity,
                                     BigDecimal totalSum, BigDecimal income) {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setAsset(asset);
        operationHistory.setCryptoId(asset.getCryptoId());
        operationHistory.setOperationType(operationType);
        operationHistory.setSumOperation(totalSum);
        operationHistory.setQuantityCurrentOperation(quantity);
        operationHistory.setIncomeCurrentOperation(income);
        operationHistory.setPurchaseDate(LocalDateTime.now());
        operationHistory.setQuantity(asset.getQuantity());

        operationHistoryRepository.save(operationHistory);
        log.info("Операция сохранена в историю: тип = {}, актив = {}, количество = {}, сумма = {}, прибыль = {}",
                operationType, asset.getCryptoId(), quantity, totalSum, income);
    }
}
