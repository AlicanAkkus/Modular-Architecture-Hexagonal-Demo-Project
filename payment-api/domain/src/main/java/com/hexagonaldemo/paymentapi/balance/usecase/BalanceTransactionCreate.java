package com.hexagonaldemo.paymentapi.balance.usecase;

import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceTransactionCreate implements UseCase {

    private Long accountId;
    private BigDecimal amount;
    private BalanceTransactionType type;

    public BigDecimal getAmountAsNumeric() {
        return type.equals(BalanceTransactionType.WITHDRAW) ? amount.multiply(new BigDecimal("-1")) : amount;
    }
}