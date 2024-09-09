package com.sbrf.student.cryptoinvest.crypto.service;

import com.sbrf.student.cryptoinvest.crypto.model.CryptoCurrency;

import java.util.List;

/**
 * Сервис получения данных о криптовалютах.
 */
public interface CryptoCurrencyService {

    /**
     * @return список всех возможных криптовалют с ценами.
     */
    List<CryptoCurrency> getAllCryptoCurrencies();

    /**
     * @param cryptoId ID криптовалюты
     * @return модель криптовалюты
     */
    CryptoCurrency getCryptoCurrency(Long cryptoId);
}
