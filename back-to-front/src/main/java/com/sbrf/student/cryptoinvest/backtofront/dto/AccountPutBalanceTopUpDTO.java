package com.sbrf.student.cryptoinvest.backtofront.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Модель DTO пополнения баланса счета для PUT запроса.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountPutBalanceTopUpDTO {

    /**
     * Идентификатор клиента.
     */
    private Long userId;

    /**
     * Сумма пополнения счета.
     */
    private String amount;

    /**
     * @return сумма пополнения счета в BigDecimal
     */
    public BigDecimal getAmountBigDecimal() {
        return new BigDecimal(amount);
    }
}
