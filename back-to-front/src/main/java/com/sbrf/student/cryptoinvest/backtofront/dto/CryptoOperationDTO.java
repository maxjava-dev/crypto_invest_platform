package com.sbrf.student.cryptoinvest.backtofront.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Модель DTO операции покупки/продажи криптовалюты.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CryptoOperationDTO {

    /**
     * Количество криптовалюты.
     */
    private String quantity;
}
