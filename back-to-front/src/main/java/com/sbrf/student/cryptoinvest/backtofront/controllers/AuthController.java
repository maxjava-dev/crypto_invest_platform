package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.sbrf.student.cryptoinvest.backtofront.services.RegistrationService;

@Controller
@RequestMapping("/")
public class AuthController {

    private final RegistrationService registrationService;

    @Autowired
    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
       return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") User user) {
        registrationService.register(user);
        return "redirect:/";
    }
}
