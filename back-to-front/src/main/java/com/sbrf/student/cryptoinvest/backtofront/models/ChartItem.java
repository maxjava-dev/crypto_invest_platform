package com.sbrf.student.cryptoinvest.backtofront.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Модель элемента истории цен для графика
 */
@Data
@AllArgsConstructor
public class ChartItem {

    /**
     * Время
     */
    private Long time;

    /**
     * Цена
     */
    private BigDecimal price;

}
