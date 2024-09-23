package com.sbrf.student.cryptoinvest.backtofront.models.crypto;

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
    private String name;

    /**
     * Описание.
     */
    private String description;

    /**
     * Url на картинку.
     */
    private String logo;

    /**
     * Цена в USD.
     */
    private BigDecimal price;
}