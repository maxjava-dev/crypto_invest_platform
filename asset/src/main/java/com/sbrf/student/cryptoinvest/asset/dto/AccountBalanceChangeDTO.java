package com.sbrf.student.cryptoinvest.asset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO для изменения баланса счета(Например при совершении операции).
 * Передача сервису users
 */
@Data
@AllArgsConstructor
public class AccountBalanceChangeDTO {
    /**
     * ID клиента, счет которого нужно изменить.
     */
    private long userId;

    /**
     * На сколько нужно изменить счет.
     */
    private String amount;

    /**
     * @return На сколько нужно изменить счет в BigDecimal.
     */
    public BigDecimal getAmount() {
        return new BigDecimal(amount);
    }
}
