package com.sbrf.student.cryptoinvest.asset.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * История операций клиента
 */
@Entity
@Data
@Table(name = "operation_history")
public class OperationHistory {
    /**
     * Id операции
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;

    /**
     * Id актива(внешний ключ к таблице {@link Asset})
     */
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "assetId", nullable = false)
    private Asset asset;

    /**
     * Id криптовалюты (поле, которое нужно для вывода в JSON)
     */
    @Column(nullable = false)
    private Long cryptoId;

    /**
     * При загрузке объекта из бд заполняется поле cryptoId
     */
    @PostLoad
    private void loadCryptoId() {
        if (asset != null) {
            this.cryptoId = asset.getCryptoId();
        }
    }

    /**
     * Тип операции(покупка/продажа {@link OperationType})
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType operationType;

    /**
     * Общая сумма операции(покупки/продажи) в данной транзакции
     */
    @Column(nullable = false)
    @PositiveOrZero(message = "Сумма операции не может быть отрицательной")
    private BigDecimal sumOperation;

    /**
     * Количество актива в данной транзакции
     */
    @Column(nullable = false)
    @PositiveOrZero(message = "Количество актива не может быть отрицательным")
    private BigDecimal quantityCurrentOperation;

    /**
     * Прибыль или убыток по данной транзакции
     */
    @Column(nullable = false)
    private BigDecimal incomeCurrentOperation;

    /**
     * Дата и время транзакции(время сервера)
     */
    @Column(nullable = false)
    @PastOrPresent(message = "Дата и время операции не могут быть в будущем")
    private LocalDateTime purchaseDate;

    /**
     * Общее количество криптовалюты после операции у клиента на счете(с учетом всех операций по этой валюте до)
     */
    @Column(nullable = false)
    @PositiveOrZero(message = "Переданное количество валюты превышает имеющееся")
    private BigDecimal quantity;
}
