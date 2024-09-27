package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.api.AssetApi;
import com.sbrf.student.cryptoinvest.backtofront.api.UserApi;
import com.sbrf.student.cryptoinvest.backtofront.dto.CryptoOperationDTO;
import com.sbrf.student.cryptoinvest.backtofront.models.asset.OperationHistoryItem;
import com.sbrf.student.cryptoinvest.backtofront.models.OperationStatus;
import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoCurrency;
import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoCurrencyInfoModel;
import com.sbrf.student.cryptoinvest.backtofront.models.user.User;
import com.sbrf.student.cryptoinvest.backtofront.services.AssetService;
import com.sbrf.student.cryptoinvest.backtofront.services.CryptoService;
import com.sbrf.student.cryptoinvest.backtofront.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/crypto")
public class CryptoController {

    private final CryptoService cryptoService;
    private final AssetService assetService;
    private final AssetApi assetApi;
    private final UserApi userApi;

    @Autowired
    public CryptoController(CryptoService cryptoService, AssetService assetService, AssetApi assetApi, UserApi userApi) {
        this.cryptoService = cryptoService;
        this.assetService = assetService;
        this.assetApi = assetApi;
        this.userApi = userApi;
    }

    @GetMapping("/")
    public String cryptoListPage(Model model) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
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
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
                });

        CryptoCurrencyInfoModel pageData = cryptoService.getCryptoCurrencyInfoModel(symbol);
        List<OperationHistoryItem> operationHistory = assetService.getOperationHistory(currentUser.getId());
        model.addAttribute("data", pageData);
        model.addAttribute("showHistoryButton", !operationHistory.isEmpty());
        return "crypto/details";
    }

    @GetMapping("/history/{symbol}")
    public String cryptoHistoryPage(Model model, @PathVariable String symbol) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
                });

        CryptoCurrencyInfoModel currencyInfoModel = cryptoService.getCryptoCurrencyInfoModel(symbol);
        CryptoCurrency cryptoCurrency = currencyInfoModel.getCryptoCurrency();
        List<OperationHistoryItem> operationHistory = assetService.getOperationHistoryByCrypto(currentUser.getId(), cryptoCurrency);
        System.out.println("operationHistory: " + operationHistory);
        model.addAttribute("operationHistory", operationHistory);
        return "crypto/history";
    }

    @GetMapping("/{id}/buy")
    public String cryptoBuyPage(Model model, @PathVariable String id) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
                });
        return "crypto/buy";
    }

    @PostMapping("/{id}/buy")
    public String cryptoPerformBuy(
            Model model,
            @PathVariable String id,
            @ModelAttribute("cryptoOperationDTO") CryptoOperationDTO cryptoOperationDTO
    ) {
        User currentUser = UserAuthentication.getCurrentUser();
        OperationStatus operationStatus = assetApi.performBuyCrypto(id, currentUser.getId().toString(), cryptoOperationDTO.getQuantity());
        if (operationStatus == OperationStatus.SUCCESS) {
            return "redirect:/assets/";
        } else {
            model.addAttribute("serverError", true);
            model.addAttribute("balance", currentUser.getBalance());
            model.addAttribute("income", currentUser.getIncome());
            model.addAttribute("username", currentUser.getVisibleUserName());
            return "crypto/buy";
        }
    }

    @GetMapping("/{id}/sell")
    public String cryptoSellPage(Model model, @PathVariable String id) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
                });
        return "crypto/sell";
    }

    @PostMapping("/{id}/sell")
    public String cryptoPerformSell(
            Model model,
            @PathVariable String id,
            @ModelAttribute("cryptoOperationDTO") CryptoOperationDTO cryptoOperationDTO
    ) {
        User currentUser = UserAuthentication.getCurrentUser();
        OperationStatus operationStatus = assetApi.performSellCrypto(id, currentUser.getId().toString(), cryptoOperationDTO.getQuantity());
        if (operationStatus == OperationStatus.SUCCESS) {
            return "redirect:/assets/";
        } else {
            model.addAttribute("serverError", true);
            model.addAttribute("balance", currentUser.getBalance());
            model.addAttribute("income", currentUser.getIncome());
            model.addAttribute("username", currentUser.getVisibleUserName());
            return "crypto/sell";
        }
    }
}
