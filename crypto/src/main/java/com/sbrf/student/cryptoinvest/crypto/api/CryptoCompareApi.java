package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.model.data.CCHistoryResponse;

/**
 * Апи для получения исторических данных о ценах
 */
public interface CryptoCompareApi {

    /**
     *
     * @param symbol символ криптовалюты
     * @return данные о ценах на последние 100 часов
     */
    CCHistoryResponse getHourlyHistoryData(String symbol);

}
