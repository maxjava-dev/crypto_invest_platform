package com.sbrf.student.cryptoinvest.backtofront.services;

import com.sbrf.student.cryptoinvest.backtofront.api.UserApi;
import com.sbrf.student.cryptoinvest.backtofront.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserApi userApi;

    @Autowired
    public UserDetailsServiceImpl(UserApi userApi) {
        this.userApi = userApi;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userApi.getUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
    }
}
