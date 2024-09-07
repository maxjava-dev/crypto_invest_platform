package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Элемент данных о цене криптовалюты из CoinMarketCap
 */
@Data
@NoArgsConstructor
public class CoinMarketCapPriceElement {
    Long id;
    Map<String, CoinMarketCapPriceQuote> quote;
}
