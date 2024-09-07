package com.sbrf.student.cryptoinvest.crypto.api;

import com.sbrf.student.cryptoinvest.crypto.model.data.CoinMarketCapIdList;
import com.sbrf.student.cryptoinvest.crypto.model.data.CoinMarketCapMetadata;
import com.sbrf.student.cryptoinvest.crypto.model.data.CoinMarketCapPriceList;

import java.util.List;

/**
 * Api для получения данных их CoinMarketCap.
 */
public interface CoinMarketCapApi {

    /**
     *
     * @param ids список id криптовалют
     * @return метаданные о криптовалютах
     */
    CoinMarketCapMetadata getMetadata(List<Long> ids);

    /**
     *
     * @param topN количество криптовалют, id которых нужно получить
     * @return список id
     */
    CoinMarketCapIdList getIdList(int topN);

    /**
     *
     * @param ids список id криптовалют
     * @return данные о ценах
     */
    CoinMarketCapPriceList getPriceList(List<Long> ids);

}
