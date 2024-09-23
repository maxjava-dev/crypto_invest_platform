package com.sbrf.student.cryptoinvest.backtofront.models.crypto;

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
     * Время в форматированном виде
     */
    private String time;

    /**
     * Цена
     */
    private BigDecimal price;

}
