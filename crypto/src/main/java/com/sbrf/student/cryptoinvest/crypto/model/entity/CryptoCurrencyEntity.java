package com.sbrf.student.cryptoinvest.crypto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Модель криптовалюты для БД.
 */
@Data
@Entity
@Table(name = "crypto")
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCurrencyEntity {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Внешний идентификатор, используется для связи с внешними сервисами.
     */
    @Column(name = "external_id")
    private Long externalId;

    /**
     * Символьный код криптовалюты.
     */
    @Column
    private String symbol;

    /**
     * Название.
     */
    @Column
    private String name;

    /**
     * Описание.
     */
    @Column(length = 4096)
    private String description;

    /**
     * Url на картинку.
     */
    @Column
    private String logo;
}
