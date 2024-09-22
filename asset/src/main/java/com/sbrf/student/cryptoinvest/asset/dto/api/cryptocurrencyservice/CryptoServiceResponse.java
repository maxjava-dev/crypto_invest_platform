package com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

/**
 * Получение данных в формате JSON о криптовалюте от сервиса CryptoCurrency.
 *  Модель использует комбинацию строго типизированных и динамических полей
 */
@Data
public class CryptoServiceResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("price")
    private BigDecimal price;

    /**
     * Map для хранения любых дополнительных динамических полей
     */
    private Map<String, Object> additionalProperties = new HashMap<>();

    /**
     * Метод, который Jackson вызовет при нахождении неизвестных полей
     *
      * @param key имя поля
     * @param value значение поля
     */
    @JsonAnySetter // при обнаружении неизвестного поля оно должно быть добавлено в additionalProperties.
    public void setAdditionalProperty(String key, Object value) {
        additionalProperties.put(key, value);
    }
}
