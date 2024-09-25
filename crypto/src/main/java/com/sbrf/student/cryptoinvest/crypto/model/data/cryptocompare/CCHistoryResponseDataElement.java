package com.sbrf.student.cryptoinvest.crypto.model.data.cryptocompare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Элемент истории цен.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CCHistoryResponseDataElement {

    /**
     * Время в секундах.
     */
    @JsonProperty("time")
    private long time;

    /**
     * Цена на начало периода (часа).
     */
    @JsonProperty("open")
    private double open;
}
