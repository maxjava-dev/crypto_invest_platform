package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Элемент данных о цене криптовалюты из CoinMarketCap
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapPriceElement {
    /**
     * Идентификатор криптовалюты
     */
    Long id;

    /**
     * Соответствие: код базовой валюты (USD) - данные о цене
     */
    Map<String, CoinMarketCapPriceQuote> quote;
}
