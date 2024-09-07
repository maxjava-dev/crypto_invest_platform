package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Цена криптовалюты из CoinMarketCap
 */
@Data
@NoArgsConstructor
public class CoinMarketCapPriceQuote {
    BigDecimal price;
}
