// TODO: поправить тесты
//package com.sbrf.student.cryptoinvest.users.controllers;
//
//import com.sbrf.student.cryptoinvest.users.dto.AccountBalanceChangeDTO;
//import com.sbrf.student.cryptoinvest.users.model.Account;
//import com.sbrf.student.cryptoinvest.users.repository.AccountRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import java.math.BigDecimal;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class AccountControllerTest {
//
//    private AccountController accountController;
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        accountController = new AccountController(accountRepository);
//    }
//
//    @Test
//    void testTopUp() {
//        // Arrange
//        long userId = 1L;
//        BigDecimal amount = BigDecimal.TEN;
//        AccountBalanceChangeDTO balanceChangeDTO = new AccountBalanceChangeDTO(userId, amount.toString());
//
//        Account account = new Account();
//        account.setBalance(BigDecimal.ZERO);
//        when(accountRepository.findById(userId)).thenReturn(Optional.of(account));
//
//        // Act
//        ResponseEntity<Void> response = accountController.topUp(balanceChangeDTO);
//
//        // Assert
//        assertEquals(ResponseEntity.ok().build(), response);
//        assertEquals(amount, account.getBalance());
//    }
//
//    @Test
//    void testTopUp_accountNotFound() {
//        // Arrange
//        long userId = 1L;
//        BigDecimal amount = BigDecimal.TEN;
//        AccountBalanceChangeDTO balanceChangeDTO = new AccountBalanceChangeDTO(userId, amount.toString());
//
//        when(accountRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // Act
//        ResponseEntity<Void> response = accountController.topUp(balanceChangeDTO);
//
//        // Assert
//        assertEquals(ResponseEntity.notFound().build(), response);
//    }
//
//    @Test
//    void testWithdraw() {
//        // Arrange
//        long userId = 1L;
//        BigDecimal amount = BigDecimal.TEN;
//        AccountBalanceChangeDTO balanceChangeDTO = new AccountBalanceChangeDTO(userId, amount.toString());
//
//        Account account = new Account();
//        account.setBalance(BigDecimal.valueOf(20L));
//        when(accountRepository.findById(userId)).thenReturn(Optional.of(account));
//
//        // Act
//        ResponseEntity<Void> response = accountController.withdraw(balanceChangeDTO);
//
//        // Assert
//        assertEquals(ResponseEntity.ok().build(), response);
//        assertEquals(BigDecimal.valueOf(10L), account.getBalance());
//    }
//
//    @Test
//    void testWithdraw_accountNotFound() {
//        // Arrange
//        long userId = 1L;
//        BigDecimal amount = BigDecimal.TEN;
//        AccountBalanceChangeDTO balanceChangeDTO = new AccountBalanceChangeDTO(userId, amount.toString());
//
//        when(accountRepository.findById(userId)).thenReturn(Optional.empty());
//
//        // Act
//        ResponseEntity<Void> response = accountController.withdraw(balanceChangeDTO);
//
//        // Assert
//        assertEquals(ResponseEntity.notFound().build(), response);
//    }
//
//    @Test
//    void testWithdraw_insufficientFunds() {
//        // Arrange
//        long userId = 1L;
//        BigDecimal amount = BigDecimal.valueOf(20L);
//        AccountBalanceChangeDTO balanceChangeDTO = new AccountBalanceChangeDTO(userId, amount.toString());
//
//        Account account = new Account();
//        account.setBalance(BigDecimal.TEN);
//        when(accountRepository.findById(userId)).thenReturn(Optional.of(account));
//
//        // Act
//        ResponseEntity<Void> response = accountController.withdraw(balanceChangeDTO);
//
//        // Assert
//        assertEquals(ResponseEntity.badRequest().build(), response);
//    }
//}