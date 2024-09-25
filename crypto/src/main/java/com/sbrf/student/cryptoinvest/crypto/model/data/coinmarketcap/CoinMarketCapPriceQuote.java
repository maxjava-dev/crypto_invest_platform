package com.sbrf.student.cryptoinvest.crypto.model.data.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Данные о цене криптовалюты из CoinMarketCap.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinMarketCapPriceQuote {
    /**
     * Цена.
     */
    @JsonProperty("price")
    private BigDecimal price;

    /**
     * Рыночная капитализация.
     */
    @JsonProperty("market_cap")
    private BigDecimal marketCap;

    /**
     * Изменение цены за последний час в процентах.
     */
    @JsonProperty("percent_change_1h")
    private BigDecimal percentChange1h;

    /**
     * Изменение цены за последний день в процентах.
     */
    @JsonProperty("percent_change_24h")
    private BigDecimal percentChange24h;

    /**
     * Изменение цены за последние 7 дней в процентах.
     */
    @JsonProperty("percent_change_7d")
    private BigDecimal percentChange7d;

    /**
     * Изменение цены за последние 30 дней в процентах.
     */
    @JsonProperty("percent_change_30d")
    private BigDecimal percentChange30d;
}
