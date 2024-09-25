package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapIdList;
import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapMetadata;
import com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap.CoinMarketCapPriceList;

import java.util.List;

/**
 * Api для получения данных их CoinMarketCap.
 */
public interface CoinMarketCapApi {

    /**
     *
     * @param ids Список id криптовалют.
     * @return Метаданные о криптовалютах.
     */
    CoinMarketCapMetadata getMetadata(List<Long> ids);

    /**
     *
     * @param topN Количество криптовалют, id которых нужно получить.
     * @return Список id.
     */
    CoinMarketCapIdList getIdList(int topN);

    /**
     *
     * @param ids Список id криптовалют.
     * @return Данные о ценах.
     */
    CoinMarketCapPriceList getPriceList(List<Long> ids);
}
