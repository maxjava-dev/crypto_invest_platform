package com.sbrf.student.cryptoinvest.asset.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Калькутор доходности актива
 */
public class AssetCalculator {

    /**
     * Расчет стоимости актива на момент покупки
     * @param oldCost - прежняя стоимость актива
     * @param oldQuantity - прежнее количество
     * @param newQuantity - новое количество
     * @return новая стоимость актива
     */
    public static BigDecimal calculateNewCost(BigDecimal oldCost, BigDecimal oldQuantity, BigDecimal newQuantity) {
        return oldQuantity.compareTo(newQuantity) == 0 ? BigDecimal.ZERO :
                oldCost.multiply(newQuantity).divide(oldQuantity, RoundingMode.HALF_UP);
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
        return totalRevenue.subtract(oldCost).add(newCost);
    }
}
