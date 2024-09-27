package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.model.data.cryptocompare.CCHistoryResponse;

/**
 * Апи для получения исторических данных о ценах из CryptoCompare.
 */
public interface CryptoCompareApi {

    /**
     *
     * @param symbol Символ криптовалюты.
     * @param count Количество элементов данных.
     * @param toTimeStamp До какого времени получать данные.
     * @return Данные о ценах.
     */
    CCHistoryResponse getHourlyHistoryData(String symbol, Long count, Long toTimeStamp);
}
