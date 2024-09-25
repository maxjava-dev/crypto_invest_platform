package com.sbrf.student.cryptoinvest.crypto.model.data.cryptocompare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Модель со списком элементов истории цен.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CCHistoryResponseData {

    /**
     * Список элементов истории цен.
     */
    @JsonProperty("Data")
    private List<CCHistoryResponseDataElement> data;
}
