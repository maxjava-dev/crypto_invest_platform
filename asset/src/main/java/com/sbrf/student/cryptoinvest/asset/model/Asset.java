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
@Table(name = "assets",
        uniqueConstraints ={@UniqueConstraint(columnNames = {"userId", "cryptoId"})})
public class Asset {
    /**
     * id приобретенного актива
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assetId;

    /**
     *  id клиента который владеет активом(foreign key к микросервису User)
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * id криптовалюты принадлежащей клиенту(foreign key к микросервису Cryptocurrency)
     */
    @Column(nullable = false)
    private Long cryptoId;

    /**
     * количество криптовалюты принадлежащей клиенту
     */
    @Column(nullable = false)
    @PositiveOrZero(message = "Переданное количество валюты превышает имеющееся")
    private BigDecimal quantity;

    /**
     * История операций, связанных с этим активом (для оптимизации)
     */
    @OneToMany(mappedBy = "asset")
    private List<OperationHistory> operationHistories;
}
