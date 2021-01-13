package com.hexagonaldemo.paymentapi.adapters.balance.jpa.entity;

import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "balance")
@Table(name = "balance")
@Where(clause = "status <> -1")
public class BalanceEntity extends AbstractEntity {

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private BigDecimal amount;

    public Balance toModel() {
        return Balance.builder()
                .id(super.getId())
                .accountId(accountId)
                .amount(amount)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}