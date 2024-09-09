package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Элемент метаданных криптовалют из CoinMarketCap
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapMetadataElement {
    /**
     * Идентификатор криптовалюты в CoinMarketCap
     */
    Long id;

    /**
     * Символьный код
     */
    String symbol;

    /**
     * Название
     */
    String name;

    /**
     * Описание
     */
    String description;

    /**
     * Url на картинку
     */
    String logo;
}
