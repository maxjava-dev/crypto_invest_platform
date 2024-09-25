package com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ID криптовалюты из CoinMarketCap.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapIdListElement {
    /**
     * Идентификатор криптовалюты.
     */
    private Long id;
}
