package com.sbrf.student.cryptoinvest.backtofront.services;

import com.sbrf.student.cryptoinvest.backtofront.api.UserApi;
import com.sbrf.student.cryptoinvest.backtofront.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserApi userApi;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, UserApi userApi) {
        this.passwordEncoder = passwordEncoder;
        this.userApi = userApi;
    }

    /**
     * @param user модель клиента для регистрации
     * @return опциональный объект клиента
     */
    @Transactional
    public Optional<User> register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userApi.postUser(user);
    }
}
