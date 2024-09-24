package com.sbrf.student.cryptoinvest.asset.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * Стоимость криптовалюты на момент покупки
     */
    @Column(nullable = false)
    @PositiveOrZero(message = "Стоимость должна быть больше или равна 0")
    private BigDecimal cost;

    /**
     * История операций, связанных с этим активом (для оптимизации)
     */
    @OneToMany(mappedBy = "asset")
    @JsonIgnore
    private List<OperationHistory> operationHistories;
}
