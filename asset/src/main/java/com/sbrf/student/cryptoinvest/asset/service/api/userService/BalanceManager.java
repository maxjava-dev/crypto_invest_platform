package com.sbrf.student.cryptoinvest.asset.service.api.userService;

import java.math.BigDecimal;

/**
 * Менеджер для работы с балансом пользователя
 */
public interface BalanceManager {
    /**
     * Метод для проверки и обновления баланса пользователя, обращение к сервису users
     * @param userId ID пользователя, для которого нужно обновить баланс
     * @param amount сумма операции
     * @param income зафиксированная прибыль или убыток
     */
    void updateUserBalance(Long userId, BigDecimal amount, BigDecimal income);
}
