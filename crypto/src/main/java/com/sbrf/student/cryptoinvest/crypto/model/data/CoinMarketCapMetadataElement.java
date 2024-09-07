package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Элемент метаданных криптовалют из CoinMarketCap
 */
@Data
@NoArgsConstructor
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
