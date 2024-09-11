package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Цена криптовалюты из CoinMarketCap
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapPriceQuote {
    /**
     * Цена
     */
    BigDecimal price;
}
