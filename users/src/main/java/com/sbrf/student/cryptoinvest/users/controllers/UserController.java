package com.sbrf.student.cryptoinvest.users.controllers;

import com.sbrf.student.cryptoinvest.users.dto.UserDTO;
import com.sbrf.student.cryptoinvest.users.model.Account;
import com.sbrf.student.cryptoinvest.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sbrf.student.cryptoinvest.users.model.User;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param username имя клиента, по которому нужно получить модель DTO клиента
     * @return модель DTO клиента
     */
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<User> foundedUser = userRepository.findByUserName(username);
        return foundedUser.map(user -> ResponseEntity.ok(user.getDTO()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * @param userDTO модель DTO клиента
     * @return модель DTO клиента
     */
    @PostMapping
    public ResponseEntity<UserDTO> postUser(@RequestBody UserDTO userDTO) {
        User newUser = new User(userDTO);
        Account newAccount = new Account();
        newAccount.setBalance(BigDecimal.ZERO);
        newAccount.setIncome(BigDecimal.ZERO);
        newUser.setAccount(newAccount);

        // TODO: Переписать работу с ошибками - https://tracker.yandex.ru/KRIPTOVALYUTY-16
        try {
            User savedUser = userRepository.save(newUser);
            return savedUser != null
                    ? ResponseEntity.ok(savedUser.getDTO())
                    : ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
