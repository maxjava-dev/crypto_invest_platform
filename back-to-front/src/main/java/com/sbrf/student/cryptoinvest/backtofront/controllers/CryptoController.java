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
        List<OperationHistoryItem> operationHistory = assetService.getOperationHistoryByCrypto(currentUser.getId(), pageData.getCryptoCurrency());
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
        model.addAttribute("operationHistory", operationHistory);
        return "crypto/history";
    }

    @GetMapping("/{symbol}/buy")
    public String cryptoBuyPage(Model model, @PathVariable String symbol) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
                });
        CryptoCurrencyInfoModel currencyInfoModel = cryptoService.getCryptoCurrencyInfoModel(symbol);
        CryptoCurrency cryptoCurrency = currencyInfoModel.getCryptoCurrency();
        model.addAttribute("crypto", cryptoCurrency);
        return "crypto/buy";
    }

    @PostMapping("/{symbol}/buy")
    public String cryptoPerformBuy(
            Model model,
            @PathVariable String symbol,
            @ModelAttribute("cryptoOperationDTO") CryptoOperationDTO cryptoOperationDTO
    ) {
        User currentUser = UserAuthentication.getCurrentUser();
        CryptoCurrencyInfoModel currencyInfoModel = cryptoService.getCryptoCurrencyInfoModel(symbol);
        CryptoCurrency cryptoCurrency = currencyInfoModel.getCryptoCurrency();
        String cryptoId = cryptoCurrency.getId().toString();
        OperationStatus operationStatus = assetApi.performBuyCrypto(cryptoId, currentUser.getId().toString(), cryptoOperationDTO.getQuantity());
        if (operationStatus == OperationStatus.SUCCESS) {
            return "redirect:/assets/";
        } else {
            model.addAttribute("serverError", true);
            model.addAttribute("balance", currentUser.getBalance());
            model.addAttribute("income", currentUser.getIncome());
            model.addAttribute("username", currentUser.getVisibleUserName());
            model.addAttribute("crypto", cryptoCurrency);
            return "crypto/buy";
        }
    }

    @GetMapping("/{symbol}/sell")
    public String cryptoSellPage(Model model, @PathVariable String symbol) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
                });
        CryptoCurrencyInfoModel currencyInfoModel = cryptoService.getCryptoCurrencyInfoModel(symbol);
        CryptoCurrency cryptoCurrency = currencyInfoModel.getCryptoCurrency();
        model.addAttribute("crypto", cryptoCurrency);
        return "crypto/sell";
    }

    @PostMapping("/{symbol}/sell")
    public String cryptoPerformSell(
            Model model,
            @PathVariable String symbol,
            @ModelAttribute("cryptoOperationDTO") CryptoOperationDTO cryptoOperationDTO
    ) {
        User currentUser = UserAuthentication.getCurrentUser();
        CryptoCurrencyInfoModel currencyInfoModel = cryptoService.getCryptoCurrencyInfoModel(symbol);
        CryptoCurrency cryptoCurrency = currencyInfoModel.getCryptoCurrency();
        String cryptoId = cryptoCurrency.getId().toString();
        OperationStatus operationStatus = assetApi.performSellCrypto(cryptoId, currentUser.getId().toString(), cryptoOperationDTO.getQuantity());
        if (operationStatus == OperationStatus.SUCCESS) {
            return "redirect:/assets/";
        } else {
            model.addAttribute("serverError", true);
            model.addAttribute("balance", currentUser.getBalance());
            model.addAttribute("income", currentUser.getIncome());
            model.addAttribute("username", currentUser.getVisibleUserName());
            model.addAttribute("crypto", cryptoCurrency);
            return "crypto/sell";
        }
    }
}
