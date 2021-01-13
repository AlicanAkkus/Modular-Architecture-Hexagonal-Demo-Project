package com.hexagonaldemo.paymentapi.balance.command;

import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceTransactionCreate {

    private BigDecimal amount;
    private BalanceTransactionType type;

    public BigDecimal getAmountAsNumeric() {
        return type.equals(BalanceTransactionType.WITHDRAW) ? amount.multiply(new BigDecimal("-1")) : amount;
    }
}