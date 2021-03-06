package com.hexagonaldemo.paymentapi.balance.service;

import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.DomainComponent;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;

@DomainComponent
public class BalanceValidator {

    public void validate(Balance balance, BalanceTransactionCreate balanceTransactionCreate) {
        if (!balance.isSufficient(balanceTransactionCreate.getAmountAsNumeric())) {
            throw new PaymentApiBusinessException("paymentapi.balance.notSufficient");
        }
    }
}
