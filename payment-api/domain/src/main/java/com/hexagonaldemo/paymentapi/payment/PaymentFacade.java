package com.hexagonaldemo.paymentapi.payment;

import com.hexagonaldemo.paymentapi.balance.BalanceFacade;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.port.PaymentDataPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentDataPort paymentDataPort;
    private final BalanceFacade balanceFacade;

    @Transactional
    public Payment pay(PaymentCreate paymentCreate) {
        Balance balance = balanceFacade.retrieve(paymentCreate.getAccountId());

        BalanceTransactionCreate balanceTransaction = BalanceTransactionCreate.builder()
                .amount(paymentCreate.getPrice())
                .type(BalanceTransactionType.WITHDRAW)
                .build();
        balanceFacade.validate(balance, balanceTransaction);

        Payment payment = paymentDataPort.create(paymentCreate);
        balanceFacade.update(balance, balanceTransaction);
        return payment;
    }
}