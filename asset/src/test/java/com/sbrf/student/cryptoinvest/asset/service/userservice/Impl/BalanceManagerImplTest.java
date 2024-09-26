package com.sbrf.student.cryptoinvest.asset.service.userservice.Impl;

import com.sbrf.student.cryptoinvest.asset.dto.AccountBalanceChangeDTO;
import com.sbrf.student.cryptoinvest.asset.service.api.userService.Impl.BalanceManagerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Тестирование класса {@link BalanceManagerImpl}
 */
public class BalanceManagerImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BalanceManagerImpl balanceManager;

    @Value("${USERS_URL}")
    private String USERS_SERVICE_UPDATE_BALANCE_URL;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тестирует метод {@link BalanceManagerImpl#updateUserBalance(Long, BigDecimal, BigDecimal)}.
     * Проверяет, что метод выполняет корректный запрос на обновление баланса.
     */
    @Test
    void testUpdateUserBalance_Success() {
        Long userId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal income = BigDecimal.valueOf(50);

        // Вызов тестируемого метода
        balanceManager.updateUserBalance(userId, amount, income);

        // Проверка, что метод put был вызван с правильным URL и DTO
        verify(restTemplate, times(1)).put(eq(USERS_SERVICE_UPDATE_BALANCE_URL +
                        "/account/topup"), any(AccountBalanceChangeDTO.class));
    }

    /**
     * Тестирует метод {@link BalanceManagerImpl#updateUserBalance(Long, BigDecimal, BigDecimal)}.
     * Проверяет, что при возникновении исключения при обновлении баланса, выбрасывается RuntimeException.
     */
    @Test
    void testUpdateUserBalance_Failure() {
        Long userId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal income = BigDecimal.valueOf(50);

        // Настраиваем мок для выбрасывания исключения
        doThrow(new RestClientException("Error")).when(restTemplate).put(anyString(), any());

        // Проверка, что выбрасывается RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            balanceManager.updateUserBalance(userId, amount, income);
        });

        // Проверка сообщения об ошибке
        Assertions.assertEquals("Ошибка при обновлении баланса счета", exception.getMessage());

        // Проверка, что метод put был вызван
        verify(restTemplate, times(1)).put(eq(USERS_SERVICE_UPDATE_BALANCE_URL
                        + "/account/topup"), any(AccountBalanceChangeDTO.class));
    }
}
