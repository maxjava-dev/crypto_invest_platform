package com.sbrf.student.cryptoinvest.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Модель DTO клиента.
 */
@Data
@AllArgsConstructor
public class UserDTO {

    /**
     * Логин.
     */
    private String username;

    /**
     * Пароль.
     */
    private String password;

    /**
     * Полное имя.
     */
    private String fullName;

    /**
     * Электронная почта.
     */
    private String email;
}
