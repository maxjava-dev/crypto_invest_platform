package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.api.UserApi;
import com.sbrf.student.cryptoinvest.backtofront.models.User;
import com.sbrf.student.cryptoinvest.backtofront.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/assets")
public class AssetController {

    private final UserApi userApi;

    @Autowired
    public AssetController(UserApi userApi) {
        this.userApi = userApi;
    }

    /**
     * @return счет клиента с балансом, список купленных активов с кнопкапи "Купить" и "Продать",
     *         кнопка "Купить активы", по которой можно перейти на страницу со списком всех активов,
     *         которые можно купить,
     *         кнопка для выхода из аккаунта
     */
    @GetMapping("/")
    public String assetsPage(Model model) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
            .ifPresent(user -> model.addAttribute("balance", user.getBalance()));
        // TODO: Обращение к микросервису Активов за купленными активами.
        return "assets";
    }
}
