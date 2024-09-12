package com.sbrf.student.cryptoinvest.asset.exception_not_used_in_the_service_yet;

/**
 * Исключение при вводе не валидных данных
 */
public class InvalidDataException extends AssetOperationException {
    public InvalidDataException(String message) {
        super(message);
    }
}
