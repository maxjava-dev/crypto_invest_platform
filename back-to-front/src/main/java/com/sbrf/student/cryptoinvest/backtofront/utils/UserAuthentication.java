package com.sbrf.student.cryptoinvest.backtofront.utils;

import com.sbrf.student.cryptoinvest.backtofront.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Класс для получения текущего авторизованного клиента.
 */
public class UserAuthentication {

    /**
     * @return текущий авторизованный клиент
     */
    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}
