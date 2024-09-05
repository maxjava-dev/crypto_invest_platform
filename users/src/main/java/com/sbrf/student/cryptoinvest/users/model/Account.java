package com.sbrf.student.cryptoinvest.users.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * Модель счета клиента.
 */
@Data
@Entity
@Table(name = "account")
public class Account {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue
    private long id;

    /**
     * Остаток денег на счету.
     */
    @Column
    @Min(value = 0, message = "Остаток денег на счету не может быть меньше нуля")
    private BigDecimal balance;

    /**
     * Клиент, которому принадлежит счет.
     */
    @OneToOne(mappedBy = "account")
    private User user;
}