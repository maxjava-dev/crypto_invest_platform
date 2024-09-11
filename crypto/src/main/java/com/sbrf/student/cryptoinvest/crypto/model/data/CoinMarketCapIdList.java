package com.sbrf.student.cryptoinvest.crypto.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Данные со списком ID криптовалют из CoinMarketCap
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapIdList {
    /**
     * Список данных об идентификаторах криптовалют
     */
    List<CoinMarketCapIdListElement> data;
}
