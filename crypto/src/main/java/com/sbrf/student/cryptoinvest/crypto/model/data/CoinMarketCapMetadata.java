package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Метаданные криптовалют из CoinMarketCap
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapMetadata {

    /**
     * Соответствие: ИД криптовалюты - метаданные
     */
    Map<String, CoinMarketCapMetadataElement> data;
}
