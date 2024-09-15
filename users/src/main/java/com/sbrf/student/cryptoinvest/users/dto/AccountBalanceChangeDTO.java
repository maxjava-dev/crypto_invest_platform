package com.sbrf.student.cryptoinvest.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Модель DTO смены баланса счета.
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
