package com.sbrf.student.cryptoinvest.backtofront.models.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * Идентификатор.
     */
    private Long id;

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

    /**
     * Баланс счета клиента.
     */
    private String balance;

    /**
     * Прибыль или убыток счета клиента.
     */
    private String income;

    /**
     * Конструктор по умолчанию.
     */
    public User() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * @return полное имя или логин в случае, если полное имя не задано
     */
    public String getVisibleUserName() {
        return fullName != null ? fullName : username;
    }

    /**
     * @return форматированное представление баланса
     */
    public String formattedBalance() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(new BigDecimal(balance));
    }

    /**
     * @return форматированное представление прибыли
     */
    public String formattedIncome() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(new BigDecimal(income));
    }
}
