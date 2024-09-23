package com.sbrf.student.cryptoinvest.crypto.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Данные о цене криптовалюты из CoinMarketCap
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapPriceQuote {
    /**
     * Цена
     */
    @JsonProperty("price")
    BigDecimal price;

    /**
     * Рыночная капитализация
     */
    @JsonProperty("market_cap")
    BigDecimal marketCap;

    /**
     * Изменение цены за последний час в процентах
     */
    @JsonProperty("percent_change_1h")
    BigDecimal percentChange1h;

    /**
     * Изменение цены за последний день в процентах
     */
    @JsonProperty("percent_change_24h")
    BigDecimal percentChange24h;

    /**
     * Изменение цены за последние 7 дней в процентах
     */
    @JsonProperty("percent_change_7d")
    BigDecimal percentChange7d;

    /**
     * Изменение цены за последние 30 дней в процентах
     */
    @JsonProperty("percent_change_30d")
    BigDecimal percentChange30d;
}
