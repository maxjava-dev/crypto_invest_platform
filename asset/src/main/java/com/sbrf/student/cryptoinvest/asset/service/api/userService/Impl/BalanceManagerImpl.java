package com.sbrf.student.cryptoinvest.asset.service.api.userService.Impl;

import com.sbrf.student.cryptoinvest.asset.dto.AccountBalanceChangeDTO;
import com.sbrf.student.cryptoinvest.asset.service.api.userService.BalanceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * Менеджер для работы с балансом пользователя через API запросы ко внешнему сервису User
 */
@Service
@RequiredArgsConstructor
public class BalanceManagerImpl implements BalanceManager {

    private final RestTemplate restTemplate;

    @Value("${USERS_URL}")
    private String USERS_SERVICE_UPDATE_BALANCE_URL;

    private final String USERS_SERVICE_UPDATE_BALANCE_SUFFIX = "/account/topup";

    public void updateUserBalance(Long userId, BigDecimal amount, BigDecimal income) {
        /**
         * Преобразование в String для корректной передачи dto
         */
        String amountStr = amount.toPlainString();
        String incomeStr = income.toPlainString();
        AccountBalanceChangeDTO balanceChangeDTO = new AccountBalanceChangeDTO(userId, amountStr, incomeStr);

        try {
            restTemplate.put(USERS_SERVICE_UPDATE_BALANCE_URL + USERS_SERVICE_UPDATE_BALANCE_SUFFIX,
                    balanceChangeDTO);
        } catch (RestClientException e) {
            throw new RuntimeException("Ошибка при обновлении баланса счета", e);
        }
    }
}
