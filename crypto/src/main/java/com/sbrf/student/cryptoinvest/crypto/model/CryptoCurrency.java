package com.sbrf.student.cryptoinvest.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

/**
 * Модель криптовалюты с метаданными и ценой
 */
@Data
@RequiredArgsConstructor
public class CryptoCurrency {
    /**
     * Идентификатор.
     */
    private final Long id;

    /**
     * Символьный код криптовалюты.
     */
    private final String symbol;

    /**
     * Название.
     */
    private final String name;

    /**
     * Описание.
     */
    private final String description;

    /**
     * Url на картинку.
     */
    private final String logo;

    /**
     * Цена в USD.
     */
    private BigDecimal price;

    /**
     * Рыночная капитализация
     */
    private BigDecimal marketCap;

    /**
     * Изменение цены за последний час в процентах
     */
    private BigDecimal percentChange1h;

    /**
     * Изменение цены за последний день в процентах
     */
    private BigDecimal percentChange24h;

    /**
     * Изменение цены за последние 7 дней в процентах
     */
    private BigDecimal percentChange7d;

    /**
     * Изменение цены за последние 30 дней в процентах
     */
    private BigDecimal percentChange30d;
}
