package com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Элемент данных о цене криптовалюты из CoinMarketCap.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapPriceElement {
    /**
     * Идентификатор криптовалюты.
     */
    private Long id;

    /**
     * Соответствие: код базовой валюты (USD) - данные о цене.
     */
    private Map<String, CoinMarketCapPriceQuote> quote;
}
