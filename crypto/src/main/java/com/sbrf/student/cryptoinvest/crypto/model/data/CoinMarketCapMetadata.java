package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Метаданные криптовалют из CoinMarketCap
 */
@Data
@NoArgsConstructor
public class CoinMarketCapMetadata {
    Map<String, CoinMarketCapMetadataElement> data;
}
