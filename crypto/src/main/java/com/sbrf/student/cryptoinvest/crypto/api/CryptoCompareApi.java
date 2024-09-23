package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.model.data.CCHistoryResponse;

/**
 * Апи для получения исторических данных о ценах
 */
public interface CryptoCompareApi {

    /**
     *
     * @param symbol символ криптовалюты
     * @param toTimeStamp до какого времени получать данные
     * @return данные о ценах
     */
    CCHistoryResponse getHourlyHistoryData(String symbol, Long toTimeStamp);

}
