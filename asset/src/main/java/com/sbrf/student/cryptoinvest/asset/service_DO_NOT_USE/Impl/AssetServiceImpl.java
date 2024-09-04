package com.sbrf.student.cryptoinvest.asset.service_DO_NOT_USE.Impl;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.repository.AssetRepository;
import com.sbrf.student.cryptoinvest.asset.service_DO_NOT_USE.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Реализация {@link AssetService}
 */
@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService { // дописать логику методов

    private final AssetRepository assetRepository;

//    @Autowired
//    public AssetSericeImpl(AssetRepository assetRepository) { this.assetRepository = assetRepository;}
//
    @Override
    public List<Asset> getAssetsByUserId(Long userId) {
        return null;
    }

    @Override
    public void buyAsset(Long cryptoId, Long userId, BigDecimal quantity) {

    }

    @Override
    public void sellAsset(Long cryptoId, Long userId, BigDecimal quantity) {

    }
}
