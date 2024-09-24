package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.api.AssetApi;
import com.sbrf.student.cryptoinvest.backtofront.api.UserApi;
import com.sbrf.student.cryptoinvest.backtofront.dto.CryptoOperationDTO;
import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoCurrency;
import com.sbrf.student.cryptoinvest.backtofront.models.user.User;
import com.sbrf.student.cryptoinvest.backtofront.services.CryptoService;
import com.sbrf.student.cryptoinvest.backtofront.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/crypto")
public class CryptoController {

    private final CryptoService cryptoService;
    private final AssetApi assetApi;
    private final UserApi userApi;

    @Autowired
    public CryptoController(CryptoService cryptoService, AssetApi assetApi, UserApi userApi) {
        this.cryptoService = cryptoService;
        this.assetApi = assetApi;
        this.userApi = userApi;
    }

    @GetMapping("/")
    public String cryptoListPage(Model model) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.getBalance());
                    model.addAttribute("income", user.getIncome());
                });

        List<CryptoCurrency> cryptos = cryptoService.getAllList();
        model.addAttribute("cryptos", cryptos);
        return "crypto/list";
    }

    @GetMapping("/{symbol}")
    public String cryptoDetailsPage(Model model, @PathVariable String symbol) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.getBalance());
                    model.addAttribute("income", user.getIncome());
                });

        var pageData = cryptoService.getCryptoCurrencyInfoModel(symbol);
        model.addAttribute("data", pageData);
        return "crypto/details";
    }

    @GetMapping("/{id}/buy")
    public String cryptoBuyPage(Model model, @PathVariable String id) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.getBalance());
                    model.addAttribute("income", user.getIncome());
                });
        return "crypto/buy";
    }

    @PostMapping("/{id}/buy")
    public String cryptoPerformBuy(
        @PathVariable String id,
        @ModelAttribute("cryptoOperationDTO") CryptoOperationDTO cryptoOperationDTO
    ) {
        User currentUser = UserAuthentication.getCurrentUser();
        assetApi.performBuyCrypto(id, currentUser.getId().toString(), cryptoOperationDTO.getQuantity());
        return "redirect:/assets/";
    }

    @GetMapping("/{id}/sell")
    public String cryptoSellPage(Model model, @PathVariable String id) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.getBalance());
                    model.addAttribute("income", user.getIncome());
                });
        return "crypto/sell";
    }

    @PostMapping("/{id}/sell")
    public String cryptoPerformSell(
        @PathVariable String id,
        @ModelAttribute("cryptoOperationDTO") CryptoOperationDTO cryptoOperationDTO
    ) {
        User currentUser = UserAuthentication.getCurrentUser();
        assetApi.performSellCrypto(id, currentUser.getId().toString(), cryptoOperationDTO.getQuantity());
        return "redirect:/assets/";
    }
}
