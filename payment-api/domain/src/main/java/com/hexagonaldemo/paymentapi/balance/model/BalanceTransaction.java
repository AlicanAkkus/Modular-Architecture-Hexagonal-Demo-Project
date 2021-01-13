package com.hexagonaldemo.paymentapi.balance.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
public class BalanceTransaction {

    private final Long id;
    private final Long balanceId;
    private final BigDecimal amount;
    private final LocalDateTime createdAt;
    private final BalanceTransactionType type;
}
