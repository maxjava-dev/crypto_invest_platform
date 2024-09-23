package com.sbrf.student.cryptoinvest.backtofront.models.crypto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Модель данных для экрана информации о криптовалюте.
 */
@Data
@AllArgsConstructor
public class CryptoCurrencyInfoModel {

    /**
     * Данные о криптовалюте
     */
    private CryptoCurrency cryptoCurrency;

    /**
     * Список цен
     */
    private List<ChartItem> historyItemList;
}
