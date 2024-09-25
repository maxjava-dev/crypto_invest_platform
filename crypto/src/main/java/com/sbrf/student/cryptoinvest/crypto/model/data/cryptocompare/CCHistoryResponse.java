package com.sbrf.student.cryptoinvest.crypto.model.data.cryptocompare;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель ответа с историей цен.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CCHistoryResponse {

    /**
     * Статус ответа.
     */
    @JsonProperty("Response")
    private String response;

    /**
     * Сообщение.
     */
    @JsonProperty("Message")
    private String message;

    /**
     * Данные.
     */
    @JsonProperty("Data")
    private CCHistoryResponseData data;

}
