package com.sbrf.student.cryptoinvest.asset.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Калькутор доходности актива
 */
@Slf4j
public class AssetCalculator {

    /**
     * Расчет стоимости актива на момент покупки
     * @param oldCost - прежняя стоимость актива
     * @param oldQuantity - прежнее количество
     * @param newQuantity - новое количество
     * @return новая стоимость актива
     */
    public static BigDecimal calculateNewCost(BigDecimal oldCost, BigDecimal oldQuantity, BigDecimal newQuantity) {
        log.debug("Расчет новой стоимости. Прежняя стоимость: {}, Прежнее количество: {}, Новое количество: {}", oldCost, oldQuantity, newQuantity);

        BigDecimal newCost = oldQuantity.compareTo(newQuantity) == 0
                ? BigDecimal.ZERO
                : oldCost.multiply(newQuantity).divide(oldQuantity, RoundingMode.HALF_UP);

        log.info("Новая стоимость рассчитана: {}", newCost);
        return newCost;
    }

    /**
     * Расчет доходности актива
     *
     * @param totalRevenue - Общая выручка от продажи актива.
     * @param oldCost - Прежняя стоимость актива на момент его покупки.
     * @param newCost - Новая стоимость актива на момент расчета доходности.
     * @return доходность актива, рассчитанная как разница между общей выручкой и прежней стоимостью,
     *         с добавлением новой стоимости актива.
     */
    public static BigDecimal calculateIncome(BigDecimal totalRevenue, BigDecimal oldCost, BigDecimal newCost) {
        log.debug("Расчет доходности. Общая выручка: {}, Прежняя стоимость: {}, Новая стоимость: {}", totalRevenue, oldCost, newCost);

        BigDecimal income = totalRevenue.subtract(oldCost).add(newCost);

        log.info("Доходность рассчитана: {}", income);
        return income;
    }
}
