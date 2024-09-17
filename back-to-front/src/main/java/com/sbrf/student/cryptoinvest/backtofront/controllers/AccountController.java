package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.api.UserApi;
import com.sbrf.student.cryptoinvest.backtofront.dto.AccountBalanceTopUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserApi userApi;

    @Autowired
    public AccountController(UserApi userApi) {
        this.userApi = userApi;
    }

    /**
     * @return форма пополнения счета клиента с кнопкой "Пополнить"
     */
    @GetMapping("/topup")
    public String topUpPage() {
        return "account/topUp";
    }

    /**
     * @param balanceTopUpDTO DTO суммы пополнения счета клиента
     * @return редирект на страницу с активами, если пополнение прошло успешно
     */
    @PostMapping("/topup")
    public String performTopUp(@ModelAttribute("balanceTopUpDTO") AccountBalanceTopUpDTO balanceTopUpDTO) {
        userApi.topUpUserBalance(balanceTopUpDTO.getAmount());
        return "redirect:/assets/";
    }
}
