package com.sbrf.student.cryptoinvest.backtofront.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Account {

    /**
     * Остаток денег на счету.
     */
    private BigDecimal balance;
}
