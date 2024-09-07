package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Данные о ценах криптовалют из CoinMarketCap
 */
@Data
@NoArgsConstructor
public class CoinMarketCapPriceList {
    Map<String, CoinMarketCapPriceElement> data;
}
