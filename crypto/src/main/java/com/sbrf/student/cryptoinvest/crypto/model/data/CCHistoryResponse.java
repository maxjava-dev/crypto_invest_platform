package com.sbrf.student.cryptoinvest.crypto.model.data;

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
     * Статус ответа
     */
    @JsonProperty("Response")
    String response;

    /**
     * Сообщение
     */
    @JsonProperty("Message")
    String message;

    /**
     * Данные
     */
    @JsonProperty("Data")
    CCHistoryResponseData data;

}
