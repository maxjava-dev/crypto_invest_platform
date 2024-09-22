package com.sbrf.student.cryptoinvest.asset.controllers;

import com.sbrf.student.cryptoinvest.asset.model.*;
import com.sbrf.student.cryptoinvest.asset.service.AssetService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Контроллер для работы с активами
 */
@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
@Slf4j
public class AssetController {

    private final AssetService assetService;

    /**
     * Получение списка активов пользователя которыми он владеет
     *
     * @param userid - id пользователя
     * @return список купленных активов пользователя
     */
    @GetMapping("/info")
    public ResponseEntity<List<Asset>> getAssets(@RequestParam Long userid) {
        log.info("Получение списка активов для пользователя с id: {}", userid);
        List<Asset> assets = assetService.getOwnedAssetsByUserId(userid);
        log.info("Найдено {} активов для пользователя с id: {}", assets.size(), userid);
        return ResponseEntity.ok(assets);
    }

    /**
     * Обработка операций покупки/продажи актива
     *
     * @param cryptoid - id актива
     * @param userid - id пользователя
     * @param quantity - количество актива для операции покупки/продажи
     * @param operationType - тип операции (buy или sell)
     */
    @PostMapping("/{operationType}")
    public ResponseEntity<Void> handleAssetTransaction(
            @RequestParam Long cryptoid,
            @RequestParam Long userid,
            @RequestParam BigDecimal quantity,
            @PathVariable String operationType) {
        log.info("Обработка транзакции: операция = {}, id актива = {}, id пользователя = {}, количество = {}",
                operationType, cryptoid, userid, quantity);
        try {
            performOperation(cryptoid, userid, quantity, operationType);
            log.info("Транзакция успешно выполнена: операция = {}, id актива = {}, id пользователя = {}, количество = {}",
                    operationType, cryptoid, userid, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Ошибка при выполнении транзакции: операция = {}, id актива = {}, id пользователя = {}, количество = {}, ошибка = {}",
                    operationType, cryptoid, userid, quantity, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Выполнение операции покупки/продажи актива
     *
     * @param cryptoid - id актива
     * @param userid - id пользователя
     * @param quantity - количество актива для операции
     * @param operationType - тип операции (BUY или SELL)
     */
    private void performOperation(Long cryptoid, Long userid, BigDecimal quantity, String operationType) {
        if (operationType == null || !EnumUtils.isValidEnum(OperationType.class, operationType.toLowerCase())) {
            log.error("Неверный тип операции: {}", operationType);
            throw new IllegalArgumentException("Неверный тип операции: " + operationType);
        }

        /**
         * Преобразование String в Enum. Значения в enum в нижнем регистре
         */
        OperationType opType = OperationType.valueOf(operationType.toLowerCase());

        switch (opType) {
            case buy:
                log.info("Выполняется операция покупки актива: id актива = {}, id пользователя = {}, " +
                        "количество = {}", cryptoid, userid, quantity);
                assetService.buyAsset(cryptoid, userid, quantity);
                break;
            case sell:
                log.info("Выполняется операция продажи актива: id актива = {}, id пользователя = {}, " +
                        "количество = {}", cryptoid, userid, quantity);
                assetService.sellAsset(cryptoid, userid, quantity);
                break;
            default:
                log.error("Неверный тип операции: {}", operationType);
                throw new IllegalArgumentException("Неверный тип операции: " + operationType);
        }
    }
}
