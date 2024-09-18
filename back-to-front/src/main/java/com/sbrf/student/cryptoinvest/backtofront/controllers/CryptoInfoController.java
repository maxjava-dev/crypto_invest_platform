package com.sbrf.student.cryptoinvest.backtofront.controllers;

import com.sbrf.student.cryptoinvest.backtofront.services.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crypto")
public class CryptoInfoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoInfoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/{symbol}")
    public String cryptoInfoPage(Model model, @PathVariable String symbol) {
        var pageData = cryptoService.getCryptoCurrencyInfoModel(symbol);
        model.addAttribute("data", pageData);
        return "cryptoInfo";
    }
}
