package com.sbrf.student.cryptoinvest.asset.rest;

import com.sbrf.student.cryptoinvest.asset.model.*;
import com.sbrf.student.cryptoinvest.asset.service.AssetService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Контроллер для работы с активами
 */
// TODO: Проверка на валидацию в контроллере реализовать, в сервисе проверка на счет и активы
@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    /**
     * Получение списка активов пользователя которыми он владеет
     * @param userid - id пользователя
     * @return список купленных активов пользователя
     */
    @GetMapping("/info")
    public ResponseEntity<List<Asset>> getAssets(@RequestParam Long userid) {
        List<Asset> assets = assetService.getOwnedAssetsByUserId(userid);
        return ResponseEntity.ok(assets);
    }

    /**
     * Обработка операций покупки/продажи актива
     * @param cryptoid - id актива
     * @param userid - id пользователя
     * @param quantity - количество актива для операции покупки/продажи
     * @param operationType - тип операции (BUY или SELL)
     */
    @PostMapping("/{operationType}")
    public ResponseEntity<Void> handleAssetTransaction(
            @RequestParam Long cryptoid,
            @RequestParam Long userid,
            @RequestParam BigDecimal quantity,
            @PathVariable OperationType operationType) throws Exception {
            performOperation(cryptoid, userid, quantity, operationType);
            return ResponseEntity.ok().build();
    }

    /**
     * Выполнение операции покупки/продажи актива
     * @param cryptoid - id актива
     * @param userid - id пользователя
     * @param quantity - количество актива для операции
     * @param operationType - тип операции (BUY или SELL)
     * @throws Exception
     */
    private void performOperation(Long cryptoid, Long userid, BigDecimal quantity, OperationType operationType)
            throws Exception {
        switch (operationType) { // будет ли регистрозависим enum, проверить
            case buy:
                assetService.buyAsset(cryptoid, userid, quantity);
                break;
            case sell:
                assetService.sellAsset(cryptoid, userid, quantity);
                break;
            default:
                // TODO: потом свои исключения и подробную обработку и валидацию
                throw new Exception("Неверный тип операции: " + operationType);
        }
    }

    // TODO: методы для истории транзакций
    //  Написать тесты
}
