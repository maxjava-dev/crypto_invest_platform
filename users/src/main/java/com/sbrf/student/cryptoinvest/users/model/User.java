package com.sbrf.student.cryptoinvest.users.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Модель клиента.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Логин.
     */
    @Column(name = "user_name")
    @NotEmpty(message = "Логин не должен быть пустым")
    @Size(min = 2, max = 100, message = "Логин должен быть от 2 до 100 символов длиной")
    private String userName;

    /**
     * Полное имя.
     */
    @Column(name = "full_name")
    @Size(min = 2, max = 100, message = "Полное имя должно быть от 2 до 100 символов длиной")
    private String fullName;

    /**
     * Электронная почта.
     */
    @Column
    @Email(message = "Некорректная почта")
    private String email;

    /**
     * Пароль.
     */
    @Column
    @Size(min = 5, max = 1000)
    private String password;

    /**
     * Счет клиента.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull(message = "У клиента должен быть счет")
    private Account account;
}
