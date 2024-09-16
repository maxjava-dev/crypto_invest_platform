package com.sbrf.student.cryptoinvest.backtofront.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Модель DTO баланса счета.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountBalanceDTO {

    /**
     * Баланс счета.
     */
    private String balance;

    /**
     * @return баланс счета в BigDecimal
     */
    public BigDecimal getBalanceBigDecimal() {
        return new BigDecimal(balance);
    }
}
