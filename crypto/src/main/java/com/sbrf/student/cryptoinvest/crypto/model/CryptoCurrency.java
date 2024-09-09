package com.sbrf.student.cryptoinvest.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Модель криптовалюты с метаданными и ценой
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrency {
    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Символьный код криптовалюты.
     */
    private String symbol;

    /**
     * Название.
     */
    String name;

    /**
     * Описание.
     */
    String description;

    /**
     * Url на картинку.
     */
    String logo;

    /**
     * Цена в USD.
     */
    BigDecimal price;
}
