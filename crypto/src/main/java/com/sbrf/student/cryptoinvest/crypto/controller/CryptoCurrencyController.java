package com.sbrf.student.cryptoinvest.crypto.controller;

import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;
import com.sbrf.student.cryptoinvest.crypto.model.HistoryItem;
import com.sbrf.student.cryptoinvest.crypto.service.CryptoCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Контроллер криптовалют.
 */
@RestController
@Slf4j
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
        log.info("getOne called with id {}", id);
        Optional<CryptoCurrency> result = cryptoCurrencyService.getCryptoCurrency(id);
        var response = result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        log.info("getOne finished with id {}, result code {}", id, response.getStatusCode());
        return response;
    }

    /**
     * @param symbol символ криптовалюты
     * @return модель криптовалюты
     */
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<CryptoCurrency> getOne(@PathVariable String symbol) {
        log.info("getOne called with symbol {}", symbol);
        Optional<CryptoCurrency> result = cryptoCurrencyService.getCryptoCurrencyBySymbol(symbol);
        var response = result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        log.info("getOne finished with symbol {}, result code {}", symbol, response.getStatusCode());
        return response;
    }

    /**
     * @return список моделей всех криптовалют
     */
    @GetMapping("/getAll")
    public ResponseEntity<CryptoCurrency[]> getAll() {
        log.info("getAll called");
        var response = ResponseEntity.ok(cryptoCurrencyService.getAllCryptoCurrencies().toArray(CryptoCurrency[]::new));
        log.info("getAll finished, result code {}", response.getStatusCode());
        return response;
    }

    /**
     * @param symbol сивол криптовалюты
     * @return список цен за последние 100 часов
     */
    @GetMapping("/history/{symbol}")
    public ResponseEntity<HistoryItem[]> getHistory(@PathVariable String symbol) {
        log.info("getHistory called with symbol {}", symbol);
        var response = ResponseEntity.ok(cryptoCurrencyService.getHistoryData(symbol).toArray(HistoryItem[]::new));
        log.info("getHistory finished with symbol {}, result code {}", symbol, response.getStatusCode());
        return response;
    }

}
