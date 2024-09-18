package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.api.AssetApi;
import com.sbrf.student.cryptoinvest.backtofront.dto.CryptoOperationDTO;
import com.sbrf.student.cryptoinvest.backtofront.models.CryptoCurrency;
import com.sbrf.student.cryptoinvest.backtofront.models.User;
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

    @Autowired
    public CryptoController(CryptoService cryptoService, AssetApi assetApi) {
        this.cryptoService = cryptoService;
        this.assetApi = assetApi;
    }

    @GetMapping("/")
    public String cryptoPage(Model model) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        List<CryptoCurrency> cryptos = cryptoService.getAll();
        model.addAttribute("cryptos", cryptos);
        return "crypto/top";
    }

    @GetMapping("/{symbol}")
    public String cryptoInfoPage(Model model, @PathVariable String symbol) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        var pageData = cryptoService.getCryptoCurrencyInfoModel(symbol);
        model.addAttribute("data", pageData);
        return "crypto/info";
    }

    @GetMapping("/{id}/buy")
    public String cryptoBuyPage(Model model, @PathVariable String id) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
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
