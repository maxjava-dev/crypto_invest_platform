package com.sbrf.student.cryptoinvest.backtofront.services;

import com.sbrf.student.cryptoinvest.backtofront.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Вызвать микросервис Клиенты для получения пользователя
        System.out.println("username: " + username);
        return new User();
    }
}
