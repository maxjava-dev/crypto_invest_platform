package com.sbrf.student.cryptoinvest.crypto.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Модель элемента истории цен.
 */
@Data
@Entity
@Table(name = "price_history")
@AllArgsConstructor
@NoArgsConstructor
public class HistoryItemEntity {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Символьный код криптовалюты.
     */
    @Column
    private String symbol;

    /**
     * Время в секундах.
     */
    @Column
    private long time;

    /**
     * Цена.
     */
    @Column
    private BigDecimal price;
}
