package com.sbrf.student.cryptoinvest.backtofront.services;

import com.sbrf.student.cryptoinvest.backtofront.api.CryptoApi;
import com.sbrf.student.cryptoinvest.backtofront.models.ChartItem;
import com.sbrf.student.cryptoinvest.backtofront.models.CryptoCurrency;
import com.sbrf.student.cryptoinvest.backtofront.models.CryptoCurrencyInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для получения данных о криптовалютах
 */
@Service
@RequiredArgsConstructor
public class CryptoService {

    private final CryptoApi cryptoApi;

    /**
     * @return список всех криптовалют
     */
    public List<CryptoCurrency> getAll() {
        var cryptos = cryptoApi.getAll();
        if (cryptos.isPresent()) {
            return cryptos.get();
        } else {
            throw new RuntimeException("Error getting all crypto");
        }
    }

    /**
     * @param symbol символ криптовалюты
     * @return данные для экрана "О криптовалюте"
     */
    public CryptoCurrencyInfoModel getCryptoCurrencyInfoModel(String symbol) {
        var response = cryptoApi.getHistory(symbol);
        var crypto = cryptoApi.getOne(symbol);
        if (response.isPresent() && crypto.isPresent()) {
            var historyItems = response.get();
            var chartData = historyItems
                    .stream()
                    .map(item -> new ChartItem(item.getTime() * 1000, item.getPrice()))
                    .toList();
            return new CryptoCurrencyInfoModel(crypto.get(), chartData);
        } else {
            throw new RuntimeException("Error getting crypto currency data");
        }
    }
}
