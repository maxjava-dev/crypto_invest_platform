package com.sbrf.student.cryptoinvest.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Модель элемента истории цен.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryItem {

    /**
     * Время в секундах.
     */
    private long time;

    /**
     * Цена.
     */
    private BigDecimal price;

}
