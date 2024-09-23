package com.sbrf.student.cryptoinvest.backtofront.services;

import com.sbrf.student.cryptoinvest.backtofront.api.AssetApi;
import com.sbrf.student.cryptoinvest.backtofront.models.asset.Asset;
import com.sbrf.student.cryptoinvest.backtofront.models.asset.OperationHistoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для получения данных об активах
 */
@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetApi assetApi;

    /**
     * @return список всех активов клиента
     */
    public List<Asset> getAssets(Long userId) {
        var assets = assetApi.getAssets(userId);
        if (assets.isPresent()) {
            return assets.get();
        } else {
            throw new RuntimeException("Error getting all user assets");
        }
    }

    /**
     * @return история операций клиента
     */
    public List<OperationHistoryItem> getOperationHistory(Long userId) {
        var operationHistory = assetApi.getOperationHistory(userId);
        if (operationHistory.isPresent()) {
            return operationHistory.get();
        } else {
            throw new RuntimeException("Error getting all user operationHistory");
        }
    }
}