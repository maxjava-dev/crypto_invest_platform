package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sbrf.student.cryptoinvest.backtofront.services.RegistrationService;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class AuthController {

    private final RegistrationService registrationService;

    @Autowired
    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    /**
     * @return форма логина c кнопками "Войти" и "Зарегистрироваться"
     */
    @GetMapping("/")
    public String loginPage() {
        return "auth/login";
    }


    /**
     * @param user модель клиента для регистрации
     * @return форма регистрации с кнопкой "Зарегистрироваться"
     */
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
       return "auth/registration";
    }

    /**
     * @param user модель клиента для регистрации
     * @param model модель для передачи сообщения об ошибке
     * @return редирект на главную страницу, если регистрация прошла успешно, иначе на страницу регистрации с сообщением об ошибке
     */
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") User user, Model model) {
        Optional<User> registeredUser = registrationService.register(user);
        if (registeredUser.isPresent()) {
            return "redirect:/";
        } else {
            model.addAttribute("serverError", true);
            return "auth/registration";
        }
    }
}
