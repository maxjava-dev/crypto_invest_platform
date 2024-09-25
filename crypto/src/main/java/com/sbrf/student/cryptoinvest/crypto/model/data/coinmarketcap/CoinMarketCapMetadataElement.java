package com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Элемент метаданных криптовалют из CoinMarketCap.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapMetadataElement {
    /**
     * Идентификатор криптовалюты в CoinMarketCap
     */
    private Long id;

    /**
     * Символьный код.
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
}
