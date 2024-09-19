package com.sbrf.student.cryptoinvest.backtofront.services;

import com.sbrf.student.cryptoinvest.backtofront.api.CryptoApi;
import com.sbrf.student.cryptoinvest.backtofront.models.ChartItem;
import com.sbrf.student.cryptoinvest.backtofront.models.CryptoCurrency;
import com.sbrf.student.cryptoinvest.backtofront.models.CryptoCurrencyInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

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
                    .map(item -> {
                                SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault());
                                sm.setTimeZone(TimeZone.getDefault());
                                String strDate = sm.format(new Date(item.getTime() * 1000));
                                return new ChartItem(strDate, item.getPrice());
                            }
                    )
                    .toList();
            return new CryptoCurrencyInfoModel(crypto.get(), chartData);
        } else {
            throw new RuntimeException("Error getting crypto currency data");
        }
    }
}
