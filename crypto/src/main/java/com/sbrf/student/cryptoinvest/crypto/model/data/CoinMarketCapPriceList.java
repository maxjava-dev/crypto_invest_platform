package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Данные о ценах криптовалют из CoinMarketCap
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapPriceList {
    /**
     * Соответствие: ИД криптовалюты - данные о ценах
     */
    Map<String, CoinMarketCapPriceElement> data;
}
