package com.sbrf.student.cryptoinvest.users.controllers;

import com.sbrf.student.cryptoinvest.users.dto.AccountBalanceChangeDTO;
import com.sbrf.student.cryptoinvest.users.model.Account;
import com.sbrf.student.cryptoinvest.users.model.User;
import com.sbrf.student.cryptoinvest.users.repository.AccountRepository;
import com.sbrf.student.cryptoinvest.users.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Autowired
    public AccountController(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    /** Пополнение счета клиента.
     * @param balanceChangeDTO DTO смены баланса счета
     * @return сообщение об успешном изменении баланса счета, если счет существует, иначе сообщение об ошибке
     */
    @PutMapping("/topup")
    public ResponseEntity<Void> topUp(@RequestBody AccountBalanceChangeDTO balanceChangeDTO) {
        Long userId = balanceChangeDTO.getUserId();
        BigDecimal amount = balanceChangeDTO.getAmount();
        BigDecimal income = balanceChangeDTO.getIncome();

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Account account = user.get().getAccount();
            account.setBalance(account.getBalance().add(amount));
            account.setIncome(account.getIncome().add(income));
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
        BigDecimal income = balanceChangeDTO.getIncome();

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Account account = user.get().getAccount();
            if (account.getBalance().compareTo(amount) >= 0) {
                account.setBalance(account.getBalance().subtract(amount));
                account.setIncome(account.getIncome().subtract(income));
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
