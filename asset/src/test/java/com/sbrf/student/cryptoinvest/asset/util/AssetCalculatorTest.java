package com.sbrf.student.cryptoinvest.asset.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестирование {@link AssetCalculator}
 */
public class AssetCalculatorTest {

    /**
     * Тест для метода {@link AssetCalculator#calculateNewCost(BigDecimal, BigDecimal, BigDecimal)}.
     * Проверяет корректность вычисления новой стоимости актива при изменении количества.
     */
    @Test
    public void calculateNewCostTest() {
        BigDecimal oldCost = new BigDecimal("100.00");
        BigDecimal oldQuantity = new BigDecimal("5");
        BigDecimal newQuantity = new BigDecimal("3");

        BigDecimal expectedNewCost = oldCost.multiply(newQuantity).divide(oldQuantity, BigDecimal.ROUND_HALF_UP);
        BigDecimal actualNewCost = AssetCalculator.calculateNewCost(oldCost, oldQuantity, newQuantity);

        assertEquals(expectedNewCost, actualNewCost);
    }

    /**
     * Тест для метода {@link AssetCalculator#calculateNewCost(BigDecimal, BigDecimal, BigDecimal)}
     * в случае, если старое количество и новое количество активов совпадают.
     * Проверяет, что результат равен нулю.
     */
    @Test
    public void calculateNewCostZeroQuantityTest() {
        BigDecimal oldCost = new BigDecimal("100.00");
        BigDecimal oldQuantity = new BigDecimal("5");
        BigDecimal newQuantity = new BigDecimal("5");

        BigDecimal expectedNewCost = BigDecimal.ZERO;
        BigDecimal actualNewCost = AssetCalculator.calculateNewCost(oldCost, oldQuantity, newQuantity);

        assertEquals(expectedNewCost, actualNewCost);
    }

    /**
     * Тест для метода {@link AssetCalculator#calculateIncome(BigDecimal, BigDecimal, BigDecimal)}.
     * Проверяет корректность расчета доходности актива как разницы между общей выручкой и
     * старой стоимостью актива с добавлением новой стоимости.
     */
    @Test
    public void calculateIncomeTest() {
        BigDecimal totalRevenue = new BigDecimal("200.00");
        BigDecimal oldCost = new BigDecimal("100.00");
        BigDecimal newCost = new BigDecimal("50.00");

        BigDecimal expectedIncome = totalRevenue.subtract(oldCost).add(newCost);
        BigDecimal actualIncome = AssetCalculator.calculateIncome(totalRevenue, oldCost, newCost);

        assertEquals(expectedIncome, actualIncome);
    }
}
