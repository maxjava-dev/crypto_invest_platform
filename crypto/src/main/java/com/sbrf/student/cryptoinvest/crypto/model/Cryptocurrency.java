package com.sbrf.student.cryptoinvest.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Cryptocurrency {
    String code;
    BigDecimal price;
}
