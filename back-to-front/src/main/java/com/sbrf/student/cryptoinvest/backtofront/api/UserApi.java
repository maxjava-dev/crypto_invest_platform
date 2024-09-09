package com.sbrf.student.cryptoinvest.backtofront.api;

import com.sbrf.student.cryptoinvest.backtofront.models.User;
import com.sbrf.student.cryptoinvest.backtofront.utils.RestTemplateClass;
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

    private final String API_PATH = "http://localhost:8081/users";
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
        ResponseEntity<User> response = restTemplate.getForEntity(API_PATH + "/" + username, User.class);
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
        ResponseEntity<User> response = restTemplate.postForEntity(API_PATH, user, User.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Optional.of(response.getBody());
        } else {
            return Optional.empty();
        }
    }
}
