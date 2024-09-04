package com.sbrf.student.cryptoinvest.asset.service_DO_NOT_USE;

import com.sbrf.student.cryptoinvest.asset.model.Asset;

import java.math.*;
import java.util.*;

/**
 * Сервис для работы с активами пользователя
 */
public interface AssetService {

    /**
     * Получение списка активов пользователя
     * @param userId ID пользователя, для которого нужно получить список активов
     * @return список активов пользователя
     */
    List<Asset> getAssetsByUserId(Long userId);


    /**
     * Покупка актива пользователя
     * @param cryptoId ID приобретаемого актива
     * @param userId ID пользователя
     * @param quantity количество актива, которое нужно приобрести
     */
    void buyAsset(Long cryptoId, Long userId, BigDecimal quantity);


    /**
     * Продажа актива пользователя
     * @param cryptoId ID актива, который нужно продать
     * @param userId ID пользователя
     * @param quantity количество актива которое нужно продать
     */
    void sellAsset(Long cryptoId, Long userId, BigDecimal quantity);
}
