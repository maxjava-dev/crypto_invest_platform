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
    Long id;
    Map<String, CoinMarketCapPriceQuote> quote;
}
