package com.sbrf.student.cryptoinvest.users.controllers;

import com.sbrf.student.cryptoinvest.users.dto.AccountBalanceChangeDTO;
import com.sbrf.student.cryptoinvest.users.model.Account;
import com.sbrf.student.cryptoinvest.users.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Контроллер для работы с счетом клиента.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /** Пополнение счета клиента.
     * @param balanceChangeDTO DTO смены баланса счета
     * @return сообщение об успешном изменении баланса счета, если счет существует, иначе сообщение об ошибке
     */
    @PutMapping("/topup")
    public ResponseEntity<Void> topUp(@RequestBody AccountBalanceChangeDTO balanceChangeDTO) {
        Long userId = balanceChangeDTO.getUserId();
        BigDecimal amount = balanceChangeDTO.getAmount();

        Optional<Account> optionalAccount = accountRepository.findById(userId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setBalance(account.getBalance().add(amount));
            accountRepository.save(account);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /** Вывод денег со счета клиента.
     * @param balanceChangeDTO DTO смены баланса счета
     * @return сообщение об успешном изменении баланса счета, если счет существует, иначе сообщение об ошибке
     */
    @PutMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody AccountBalanceChangeDTO balanceChangeDTO) {
        Long userId = balanceChangeDTO.getUserId();
        BigDecimal amount = balanceChangeDTO.getAmount();

        Optional<Account> optionalAccount = accountRepository.findById(userId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getBalance().compareTo(amount) >= 0) {
                account.setBalance(account.getBalance().subtract(amount));
                accountRepository.save(account);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
