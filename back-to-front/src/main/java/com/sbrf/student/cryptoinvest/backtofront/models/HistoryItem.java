package com.sbrf.student.cryptoinvest.backtofront.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Модель элемента истории цен
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryItem {

    /**
     * Время в секундах
     */
    @JsonProperty("time")
    private long time;

    /**
     * Цена
     */
    @JsonProperty("price")
    private BigDecimal price;
}