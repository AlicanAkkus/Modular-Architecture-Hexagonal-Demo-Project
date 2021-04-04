package com.hexagonaldemo.paymentapi.payment;

import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.balance.BalanceFacade;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.port.PaymentDataPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentFacade {

    private final BalanceFacade balanceFacade;
    private final AccountFacade accountFacade;
    private final PaymentDataPort paymentDataPort;

    @Transactional
    public Payment pay(PaymentCreate paymentCreate) {
        accountFacade.makeBusy(paymentCreate.getAccountId());
        BalanceTransactionCreate balanceTransaction = buildBalanceTransactionCreate(paymentCreate);
        balanceFacade.validate(balanceTransaction);

        Payment payment = paymentDataPort.create(paymentCreate);
        balanceFacade.create(balanceTransaction);
        accountFacade.makeFree(paymentCreate.getAccountId());

        log.info("Total {} paid from {}", paymentCreate.getPrice(), paymentCreate.getAccountId());
        return payment;
    }

    private BalanceTransactionCreate buildBalanceTransactionCreate(PaymentCreate paymentCreate) {
        return BalanceTransactionCreate.builder()
                .amount(paymentCreate.getPrice())
                .type(BalanceTransactionType.WITHDRAW)
                .accountId(paymentCreate.getAccountId())
                .build();
    }
}