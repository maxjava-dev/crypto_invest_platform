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
@Table(name = "users")
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
    String name;

    /**
     * Описание.
     */
    @Column(length = 4096)
    String description;

    /**
     * Url на картинку.
     */
    @Column
    String logo;
}
