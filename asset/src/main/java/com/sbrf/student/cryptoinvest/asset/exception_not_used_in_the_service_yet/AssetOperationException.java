package com.sbrf.student.cryptoinvest.asset.exception_not_used_in_the_service_yet;

/**
 * Общее исключение для операций с активами
 *  TODO: доделать, внедрить в код если нужно будет
 */
public class AssetOperationException extends RuntimeException {
    public AssetOperationException(String message) {
        super(message);
    }
}
