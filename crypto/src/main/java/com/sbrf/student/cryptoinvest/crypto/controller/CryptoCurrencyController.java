package com.sbrf.student.cryptoinvest.crypto.controller;

import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;
import com.sbrf.student.cryptoinvest.crypto.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер криптовалют.
 */
@RestController
@RequestMapping("/crypto")
@RequiredArgsConstructor
public class CryptoCurrencyController {

    private final CryptoCurrencyService cryptoCurrencyService;

    /**
     * @param id идентификатор криптовалюты
     * @return модель криптовалюты
     */
    @GetMapping("/{id}")
    public ResponseEntity<CryptoCurrency> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(cryptoCurrencyService.getCryptoCurrency(id));
    }

    /**
     * @return список моделей всех криптовалют
     */
    @GetMapping("/getAll")
    public ResponseEntity<CryptoCurrency[]> getAll() {
        return ResponseEntity.ok(cryptoCurrencyService.getAllCryptoCurrencies().toArray(CryptoCurrency[]::new));
    }
}
