package com.sbrf.student.cryptoinvest.backtofront.models.asset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

/**
 * Модель актива.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Asset {
    /**
     * Id приобретенного актива
     */
    private Long assetId;

    /**
     *  Id клиента который владеет активом(foreign key к микросервису User)
     */
    private Long userId;

    /**
     * Id криптовалюты принадлежащей клиенту(foreign key к микросервису Cryptocurrency)
     */
    private Long cryptoId;

    /**
     * Количество криптовалюты принадлежащей клиенту
     */
    private BigDecimal quantity;

    /**
     * Стоимость криптовалюты на момент покупки
     */
    private BigDecimal cost;

    /**
     * Стоимость криптовалюты на текущий момент
     */
    public BigDecimal getCurrentCost() {
        return crypto.getPrice().multiply(quantity);
    }

    /**
     * Прибыль или убыток криптовалюты на текущий момент
     */
    public BigDecimal getIncome() {
        return getCurrentCost().subtract(cost);
    }

    /**
     * Прибыль или убыток криптовалюты на текущий момент отформатированный
     */
    public String getFormattedIncome() {
        String prefix = getIncome().compareTo(BigDecimal.ZERO) >= 0 ? "⏶" : "⏷";
        BigDecimal incomePercent = getIncome().divide(cost, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        return prefix + String.format(Locale.US, "%.2f", getIncome().abs()) + " (" +
                String.format(Locale.US, "%.4f", incomePercent.abs()) + "%)";
    }
    /**
     * Модель криптовалюты с метаданными и ценой.
     */
    private CryptoCurrency crypto;
}
