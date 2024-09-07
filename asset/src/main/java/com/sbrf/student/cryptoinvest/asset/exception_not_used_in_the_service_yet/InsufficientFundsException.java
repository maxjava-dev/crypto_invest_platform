package com.sbrf.student.cryptoinvest.asset.exception_not_used_in_the_service_yet;

/**
 * Исключение при недостатке средств для операции на счете((при покупке со счета через валюту))
 */
public class InsufficientFundsException extends AssetOperationException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
