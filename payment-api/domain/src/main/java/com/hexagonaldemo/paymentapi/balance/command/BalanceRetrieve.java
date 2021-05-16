package com.hexagonaldemo.paymentapi.balance.command;

import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.common.model.Command;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceRetrieve implements Command {

    private Long accountId;

    public static BalanceRetrieve from(Long accountId) {
        return BalanceRetrieve.builder().accountId(accountId).build();
    }
}