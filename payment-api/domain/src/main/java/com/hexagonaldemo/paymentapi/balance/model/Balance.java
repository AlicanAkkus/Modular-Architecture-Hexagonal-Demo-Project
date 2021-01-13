package com.hexagonaldemo.paymentapi.balance.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@With
@ToString
public class Balance {

    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isSufficient(BigDecimal amount) {
        return this.amount.add(amount).compareTo(BigDecimal.ZERO) >= 0;
    }
}