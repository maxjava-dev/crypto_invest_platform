package com.sbrf.student.cryptoinvest.users.model;

import com.sbrf.student.cryptoinvest.users.dto.UserDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

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
    @Column(name = "user_name", unique = true)
    @NotEmpty(message = "Логин не должен быть пустым")
    @Size(min = 1, max = 20, message = "Логин должен быть от 1 до 20 символов длиной")
    private String userName;

    /**
     * Пароль.
     */
    @Column
    @Size(min = 5, max = 1000)
    private String password;

    /**
     * Полное имя.
     */
    @Column(name = "full_name")
    @Size(min = 1, max = 100, message = "Полное имя должно быть от 1 до 100 символов длиной")
    private String fullName;

    /**
     * Электронная почта.
     */
    @Column
    @Email(message = "Некорректная почта")
    private String email;

    /**
     * Счет клиента.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", nullable = false)
    @NotNull(message = "У клиента должен быть счет")
    private Account account;

    public User() {}

    public User(String userName, String password, String fullName, String email) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    public User(UserDTO user) {
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.fullName = (user.getFullName() == null || user.getFullName().isEmpty())
                ? null
                : user.getFullName();
        this.email = (user.getEmail() == null || user.getEmail().isEmpty())
                ? null
                : user.getEmail();
    }

    public UserDTO getDTO() {
        return new UserDTO(id, userName, password, fullName, email, account.getBalance().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }
}
