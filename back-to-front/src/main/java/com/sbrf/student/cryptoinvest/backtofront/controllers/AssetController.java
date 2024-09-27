package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.api.UserApi;
import com.sbrf.student.cryptoinvest.backtofront.models.asset.Asset;
import com.sbrf.student.cryptoinvest.backtofront.models.asset.OperationHistoryItem;
import com.sbrf.student.cryptoinvest.backtofront.models.crypto.CryptoCurrency;
import com.sbrf.student.cryptoinvest.backtofront.models.user.User;
import com.sbrf.student.cryptoinvest.backtofront.services.AssetService;
import com.sbrf.student.cryptoinvest.backtofront.services.CryptoService;
import com.sbrf.student.cryptoinvest.backtofront.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/assets")
public class AssetController {

    private final UserApi userApi;
    private final AssetService assetService;
    private final CryptoService cryptoService;

    @Autowired
    public AssetController(UserApi userApi, AssetService assetService, CryptoService cryptoService) {
        this.userApi = userApi;
        this.assetService = assetService;
        this.cryptoService = cryptoService;
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
                .ifPresent(user -> {
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
                });

        List<Asset> assets = assetService.getAssets(currentUser.getId());
        List<Asset> notEmptyAssets = assets.stream()
            .filter(asset -> asset.getQuantity().compareTo(BigDecimal.ZERO) > 0)
            .toList();
        if (!notEmptyAssets.isEmpty()) {
            Map<Long, CryptoCurrency> cryptos = cryptoService.getAllMap();
            assets.forEach(
                asset -> asset.setCrypto(
                    cryptos.get(asset.getCryptoId())
                )
            );
        }
        model.addAttribute("assets", notEmptyAssets);
        model.addAttribute("showHistoryButton", !assets.isEmpty());

        return "asset/assets";
    }

    /**
     * @return история операций с активами клиента
     */
    @GetMapping("/history")
    public String assetsOperationHistoryPage(Model model) {
        User currentUser = UserAuthentication.getCurrentUser();
        model.addAttribute("username", currentUser.getVisibleUserName());
        userApi.getUserByUsername(currentUser.getUsername())
                .ifPresent(user -> {
                    model.addAttribute("balance", user.formattedBalance());
                    model.addAttribute("income", user.formattedIncome());
                });

        List<OperationHistoryItem> operationHistory = assetService.getOperationHistory(currentUser.getId());
        if (!operationHistory.isEmpty()) {
            Map<Long, CryptoCurrency> cryptos = cryptoService.getAllMap();
            operationHistory.forEach(
                operationHistoryItem -> operationHistoryItem.setCrypto(
                    cryptos.get(operationHistoryItem.getCryptoId())
                )
            );
        }
        model.addAttribute("operationHistory", operationHistory);

        return "asset/history";
    }
}
