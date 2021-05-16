package com.hexagonaldemo.paymentapi;

import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.account.port.LockPort;
import com.hexagonaldemo.paymentapi.adapters.BalanceFakeAdapter;
import com.hexagonaldemo.paymentapi.adapters.PaymentFakeAdapter;
import com.hexagonaldemo.paymentapi.balance.BalanceCompensateCommandHandler;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.payment.PaymentRollbackCommandHandler;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.model.PaymentState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentRollbackTest {

    @Test
    void should_create_payment_when_balance_is_sufficient() {
        doRollback(1L, "10.00", "10.00", "20.00");
        doRollback(1L, "4.50", "0.00", "4.50");
    }

    private void doRollback(Long accountId, String price, String currentBalanceAmount, String balanceAmountAfterRollback) {
        // given
        Balance currentBalance = Balance.builder()
                .id(1L)
                .accountId(accountId)
                .amount(new BigDecimal(currentBalanceAmount))
                .build();

        Balance balanceAfterRollback = Balance.builder()
                .id(1L)
                .accountId(accountId)
                .amount(new BigDecimal(balanceAmountAfterRollback))
                .build();

        Payment existingPaymentToRollback = Payment.builder()
                .id(1L)
                .accountId(accountId)
                .accountId(accountId)
                .price(new BigDecimal(price))
                .referenceCode("ref1")
                .state(PaymentState.SUCCESS)
                .build();

        // and
        BalanceFakeAdapter balancePort = new BalanceFakeAdapter(currentBalance, currentBalance);
        PaymentFakeAdapter paymentPort = new PaymentFakeAdapter(existingPaymentToRollback);

        BalanceCompensateCommandHandler balanceCompensateCommandHandler = new BalanceCompensateCommandHandler(balancePort);
        PaymentRollbackCommandHandler paymentRollbackCommandHandler = new PaymentRollbackCommandHandler(balanceCompensateCommandHandler,
                new AccountFacade(retrieveFakeAccountLockPort()), paymentPort);

        //when
        PaymentRollback paymentRollback = PaymentRollback.builder()
                .id(1L)
                .accountId(existingPaymentToRollback.getAccountId())
                .price(existingPaymentToRollback.getPrice())
                .build();

        paymentRollbackCommandHandler.handle(paymentRollback);

        //then
        assertThat(balancePort.retrieve(paymentRollback.getAccountId())).isNotNull()
                .isEqualTo(balanceAfterRollback);

        assertThat(paymentPort.retrieve(1L)).isNotNull()
                .isEqualTo(existingPaymentToRollback.withState(PaymentState.ROLLBACKED));
    }

    private LockPort retrieveFakeAccountLockPort() {
        return new LockPort() {
            @Override
            public void lock(Long accountId) {
            }

            @Override
            public void unlock(Long accountId) {
            }
        };
    }

}
