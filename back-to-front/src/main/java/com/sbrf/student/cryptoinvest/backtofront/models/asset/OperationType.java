package com.sbrf.student.cryptoinvest.backtofront.models.asset;

/**
 * Тип операции с активом
 */
public enum OperationType {
    buy,
    sell;

    @Override
    public String toString() {
        String result = "";
        switch (this) {
            case buy:
                result = "Покупка";
            break;
            case sell:
                result = "Продажа";
            break;
        }
        return result;
    }
}
