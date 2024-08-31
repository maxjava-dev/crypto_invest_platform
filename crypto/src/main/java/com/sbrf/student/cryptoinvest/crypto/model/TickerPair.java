package com.sbrf.student.cryptoinvest.crypto.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TickerPair {
    String symbol;
    BigDecimal price;
}
