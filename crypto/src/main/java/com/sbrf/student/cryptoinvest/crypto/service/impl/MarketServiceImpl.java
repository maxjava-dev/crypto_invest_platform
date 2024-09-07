package com.sbrf.student.cryptoinvest.crypto.service.impl;

import com.sbrf.student.cryptoinvest.crypto.api.CoinMarketCapApi;
import com.sbrf.student.cryptoinvest.crypto.model.Cryptocurrency;
import com.sbrf.student.cryptoinvest.crypto.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация {@link MarketService}.
 */
@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final CoinMarketCapApi api;

    @Override
    public List<Cryptocurrency> getAllCurrencies() {

        // TODO

        return null;
    }

    @Override
    public List<Cryptocurrency> getPrice(Long cryptoId) {

        // TODO

        return null;
    }
}
