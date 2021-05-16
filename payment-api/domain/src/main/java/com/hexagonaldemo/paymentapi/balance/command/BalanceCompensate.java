package com.hexagonaldemo.paymentapi.balance.command;

import com.hexagonaldemo.paymentapi.common.model.Command;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceCompensate implements Command {

    private Long accountId;
    private BigDecimal amount;


    public static BalanceCompensate from(Payment payment) {
        return BalanceCompensate.builder()
                .accountId(payment.getAccountId())
                .amount(payment.getPrice())
                .build();
    }
}