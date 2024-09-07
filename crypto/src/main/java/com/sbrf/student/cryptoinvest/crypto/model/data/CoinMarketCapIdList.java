package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Данные со списком ID криптовалют из CoinMarketCap
 */
@Data
@NoArgsConstructor
public class CoinMarketCapIdList {
    List<CoinMarketCapIdListElement> data;
}
