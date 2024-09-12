package com.sbrf.student.cryptoinvest.asset.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Активы клиента
 */
@Entity
@Data
@Table(name = "assets", uniqueConstraints ={@UniqueConstraint(columnNames = {"userId", "cryptoId"})})
public class Asset {
    /**
     * Id приобретенного актива
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    /**
     *  Id клиента который владеет активом(foreign key к микросервису User)
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * Id криптовалюты принадлежащей клиенту(foreign key к микросервису Cryptocurrency)
     */
    @Column(nullable = false)
    private Long cryptoId;

    /**
     * Количество криптовалюты принадлежащей клиенту
     */
    @Column(nullable = false)
    @PositiveOrZero(message = "Переданное количество валюты превышает имеющееся")
    private BigDecimal quantity;

    /**
     * История операций, связанных с этим активом (для оптимизации)
     * TODO: сделать историю операций далее
     *  Проанализировать еще раз структура бд
     */
    @OneToMany(mappedBy = "asset")
    private List<OperationHistory> operationHistories;
}
