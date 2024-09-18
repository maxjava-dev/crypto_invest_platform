package com.sbrf.student.cryptoinvest.backtofront.models;

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
     * Символ
     */
    private String symbol;

    /**
     * Список цен
     */
    private List<ChartItem> historyItemList;

    // TODO: Добавить остальные данные
}
