package com.sbrf.student.cryptoinvest.asset.exception_not_used_in_the_service_yet;

/**
 * Исключение при продаже активов (переданное количество криптовалюты превышает имеющееся)
 */
public class InsufficientAssetsException extends AssetOperationException{
    public InsufficientAssetsException(String message) {
        super(message);
    }
}
