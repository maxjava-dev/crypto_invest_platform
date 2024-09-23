package com.sbrf.student.cryptoinvest.backtofront.models.asset;

import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoCurrency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
     * Модель криптовалюты с метаданными и ценой.
     */
    private CryptoCurrency crypto;
}
