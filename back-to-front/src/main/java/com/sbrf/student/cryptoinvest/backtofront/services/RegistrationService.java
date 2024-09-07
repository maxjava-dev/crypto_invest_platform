package com.sbrf.student.cryptoinvest.backtofront.services;

import com.sbrf.student.cryptoinvest.backtofront.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // TODO: Вызвать микросервис Клиенты для сохранения пользователя
    }
}
