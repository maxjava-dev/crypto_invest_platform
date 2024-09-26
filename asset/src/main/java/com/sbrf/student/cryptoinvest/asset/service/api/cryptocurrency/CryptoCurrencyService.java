package com.sbrf.student.cryptoinvest.asset.service.api.cryptocurrency;

import com.sbrf.student.cryptoinvest.asset.dto.api.cryptocurrencyservice.CryptoServiceResponse;

/**
 * Интерфейс для работы с внешним сервисом CryptoCurrency
 */
public interface CryptoCurrencyService {

    /**
     * Метод для получения данных о криптовалюте по её ID
     *
     * @param cryptoId ID криптовалюты
     * @return Данные о криптовалюте
     */
    CryptoServiceResponse fetchCryptoData(Long cryptoId);
}
