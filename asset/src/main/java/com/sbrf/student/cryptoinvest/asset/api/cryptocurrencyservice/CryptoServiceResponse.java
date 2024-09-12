package com.sbrf.student.cryptoinvest.asset.api.cryptocurrencyservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Получение данных в формает JSON о криптовалюте от сервиса CryptoCurrency
 *  TODO:(можно ли автоматизировать как то в случае изменяемости полей)
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
}
