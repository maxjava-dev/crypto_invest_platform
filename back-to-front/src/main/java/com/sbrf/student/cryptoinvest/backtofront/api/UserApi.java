package com.sbrf.student.cryptoinvest.backtofront.api;

import com.sbrf.student.cryptoinvest.backtofront.dto.AccountPutBalanceTopUpDTO;
import com.sbrf.student.cryptoinvest.backtofront.models.User;
import com.sbrf.student.cryptoinvest.backtofront.utils.RestTemplateClass;
import com.sbrf.student.cryptoinvest.backtofront.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * API для обращения к микросервису Клиенты.
 */
@Component
public class UserApi {

    private final String USERS_API_PATH = "http://localhost:8081/users";
    private final String ACCOUNT_API_PATH = "http://localhost:8081/account";
    private final RestTemplateClass restTemplate;

    @Autowired
    public UserApi(RestTemplateClass restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Получить клиента по имени.
     * @param username логин клиента
     * @return опциональный объект клиента
     */
    public Optional<User> getUserByUsername(String username) {
        ResponseEntity<User> response = restTemplate.getForEntity(USERS_API_PATH + "/" + username, User.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Optional.of(response.getBody());
        } else {
            return Optional.empty();
        }
    }

    /**
     * Сохранить клиента.
     * @param user объект клиента
     * @return опциональный объект клиента
     */
    public Optional<User> postUser(User user) {
        ResponseEntity<User> response = restTemplate.postForEntity(USERS_API_PATH, user, User.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Optional.of(response.getBody());
        } else {
            return Optional.empty();
        }
    }

    /** Пополнить баланс клиента.
     * @param amount сумма для пополнения баланса
     */
    public void topUpUserBalance(String amount) {
        Long currentUserId = UserAuthentication.getCurrentUser().getId();
        System.out.println("userId: " + currentUserId);
        AccountPutBalanceTopUpDTO putBalanceTopUpDTO = new AccountPutBalanceTopUpDTO(currentUserId, amount);
        restTemplate.put(
            ACCOUNT_API_PATH + "/topup", putBalanceTopUpDTO, AccountPutBalanceTopUpDTO.class
        );
    }
}
