package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import org.springframework.stereotype.Component;

@Component
public class BalanceValidator {

    public void validate(Balance balance, BalanceTransactionCreate balanceTransactionCreate) {
        if (!balance.isSufficient(balanceTransactionCreate.getAmountAsNumeric())) {
            throw new PaymentApiBusinessException("paymentapi.balance.notSufficient");
        }
    }
}
