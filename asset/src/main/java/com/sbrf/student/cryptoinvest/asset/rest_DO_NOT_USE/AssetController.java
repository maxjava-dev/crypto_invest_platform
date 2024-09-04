package com.sbrf.student.cryptoinvest.asset.rest_DO_NOT_USE;

import com.sbrf.student.cryptoinvest.asset.model.Asset;
import com.sbrf.student.cryptoinvest.asset.service_DO_NOT_USE.Impl.AssetServiceImpl;
import org.slf4j.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/assets")
public class AssetController {
    AssetServiceImpl assetService;
    Logger logger = LoggerFactory.getLogger(AssetController.class);

    /**
     * Получение списка купленных активов
     * @param id
     * @return List<Asset>
     */
    @GetMapping("/info")
    public List<Asset> getAssets(@RequestParam Long id)
    {
        return assetService.getAssetsByUserId(id);
    }

    /**
     * Покупка актива конкретным пользователем
     * @param cryptoid
     * @param userid
     * @param quantity
     */
    @PostMapping("/buy")
    public ResponseEntity<Void> buyAsset(@RequestParam Long cryptoid, @RequestParam Long userid,
                                         @RequestParam BigDecimal quantity){
        try {
            assetService.buyAsset(cryptoid, userid, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Логирование ошибки
            logger.error("Ошибка при покупке актива {} пользователем {} с количеством {}", cryptoid, userid, quantity, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Продажа актива конкретным пользователем
     * @param cryptoid
     * @param userid
     * @param quantity
     */
    @PostMapping("/sell")
    public ResponseEntity<Void> sellAsset(@RequestParam Long cryptoid, @RequestParam Long userid,
                                          @RequestParam BigDecimal quantity){
        try {
            assetService.sellAsset(cryptoid, userid, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            // Логирование ошибки
            logger.error("Ошибка при продаже актива {} пользователем {} с количеством {}", cryptoid, userid, quantity, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
