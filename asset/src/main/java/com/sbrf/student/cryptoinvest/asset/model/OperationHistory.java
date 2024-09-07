package com.sbrf.student.cryptoinvest.asset.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * История операций конкретного клиента
 * TODO: реализовать в контроллере, сервисе и т.д.
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
    @JoinColumn(name = "assetId", nullable = false)
    private Asset asset;

    /**
     * тип операции(покупка/продажа {@link OperationType})
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType operationType;

    /**
     * сумма операции(покупки/продажи)
     */
    @Column(nullable = false)
    @PositiveOrZero(message = "Сумма операции не может быть отрицательной")
    private BigDecimal sumOperation;

    /**
     * Дата и время транзакции(время сервера)
     */
    @Column(nullable = false)
    @PastOrPresent(message = "Дата и время операции не могут быть в будущем")
    private LocalDateTime purchaseDate;

    /**
     * Количество криптовалюты после операции у клиента на счете
     */
    @Column(nullable = false)
    @PositiveOrZero(message = "Переданное количество валюты превышает имеющееся")
    private BigDecimal quantity;
}
