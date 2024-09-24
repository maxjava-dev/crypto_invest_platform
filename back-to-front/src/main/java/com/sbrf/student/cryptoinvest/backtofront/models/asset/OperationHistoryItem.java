package com.sbrf.student.cryptoinvest.backtofront.models.asset;

import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Модель элемента истории операции c активом
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationHistoryItem {
    /**
     * Id операции
     */
    private Long operationId;

    /**
     * Id криптовалюты (поле, которое нужно для вывода в JSON)
     */
    private Long cryptoId;

    /**
     * Тип операции (покупка/продажа {@link OperationType})
     */
    private OperationType operationType;

    /**
     * Общая сумма операции(покупки/продажи) в данной транзакции
     */
    private BigDecimal sumOperation;

    /**
     * Количество актива в данной транзакции
     */
    private BigDecimal quantityCurrentOperation;

    /**
     * Прибыль или убыток актива от данной транзакции
     */
    private BigDecimal incomeCurrentOperation;

    /**
     * Дата и время транзакции(время сервера)
     */
    private LocalDateTime purchaseDate;

    /**
     * Общее количество криптовалюты после операции у клиента на счете(с учетом всех операций по этой валюте до)
     */
    private BigDecimal quantity;

    /**
     * Модель криптовалюты с метаданными и ценой.
     */
    private CryptoCurrency crypto;

    /**
     * @return дата и время совершения операции в формате "dd-MM-yyyy HH:mm"
     */
    public String getFormattedPurchaseDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return purchaseDate.format(formatter);
    }
}
