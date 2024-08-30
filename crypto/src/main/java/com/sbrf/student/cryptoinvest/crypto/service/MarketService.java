package com.sbrf.student.cryptoinvest.crypto.service;

import com.sbrf.student.cryptoinvest.crypto.model.Cryptocurrency;

import java.util.List;

/**
 * Сервис получения данных рынка из внешних источников.
 */
public interface MarketService {

    /**
     * @return список всех возможных криптовалют с ценами
     */
    List<Cryptocurrency> getAllCurrencies();

}
