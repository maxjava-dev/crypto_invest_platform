package com.sbrf.student.cryptoinvest.backtofront.models.crypto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Модель криптовалюты с метаданными и ценой
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoCurrency {
    /**
     * Идентификатор.
     */
    private Long id;

    /**
     * Символьный код криптовалюты.
     */
    private String symbol;

    /**
     * Название.
     */
    private String name;

    /**
     * Описание.
     */
    private String description;

    /**
     * Url на картинку.
     */
    private String logo;

    /**
     * Цена в USD.
     */
    private BigDecimal price;

    /**
     * Рыночная капитализация
     */
    private BigDecimal marketCap;

    /**
     * Изменение цены за последний час в процентах
     */
    private BigDecimal percentChange1h;

    /**
     * Изменение цены за последний день в процентах
     */
    private BigDecimal percentChange24h;

    /**
     * Изменение цены за последние 7 дней в процентах
     */
    private BigDecimal percentChange7d;

    /**
     * Изменение цены за последние 30 дней в процентах
     */
    private BigDecimal percentChange30d;

    /**
     * @return строковое представление цены криптовалюты (два знака после .)
     */
    public String getFormattedPriceTwoFractionals() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(price);
    }

    /**
     * @return строковое представление цены криптовалюты (все знаки после . сохранены)
     */
    public String getFormattedPriceAllFractionals() {
        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.US);
        formatter.setMaximumFractionDigits(price.scale());
        return "$" + formatter.format(price);
    }

    /**
     * @return строковое представление рыночной капитализации
     */
    public String getFormattedMarketCap() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        formatter.setMaximumFractionDigits(0);
        return formatter.format(marketCap);
    }

    /**
     * @return строковое представление процента за последний час
     */
    public String getFormattedPercentChange1h() {
        return getFormattedPercent(percentChange1h);
    }

    /**
     * @return строковое представление процента за последний день
     */
    public String getFormattedPercentChange24h() {
        return getFormattedPercent(percentChange24h);
    }

    /**
     * @return строковое представление процента за последние 7 дней
     */
    public String getFormattedPercentChange7d() {
        return getFormattedPercent(percentChange7d);
    }

    /**
     * @return строковое представление пр   оцента за последние 30 дней
     */
    public String getFormattedPercentChange30d() {
        return getFormattedPercent(percentChange30d);
    }

    private String getFormattedPercent(BigDecimal percent) {
        String prefix = percent.compareTo(BigDecimal.ZERO) > 0 ? "⏶" : "⏷";
        return prefix + String.format(Locale.US, "%.2f", percent.abs()) + "%";
    }
}