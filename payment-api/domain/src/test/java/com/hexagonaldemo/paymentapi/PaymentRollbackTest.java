package com.hexagonaldemo.paymentapi;

import com.hexagonaldemo.paymentapi.account.AccountFacade;
import com.hexagonaldemo.paymentapi.account.port.LockPort;
import com.hexagonaldemo.paymentapi.adapters.BalanceFakeAdapter;
import com.hexagonaldemo.paymentapi.adapters.PaymentFakeAdapter;
import com.hexagonaldemo.paymentapi.balance.BalanceCompensateCommandHandler;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import com.hexagonaldemo.paymentapi.payment.PaymentRollbackCommandHandler;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.model.PaymentState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PaymentRollbackTest {

    @Test
    void should_allow_rollback() {
        // given
        long accountId = 1L;
        double price = 10.00;
        double currentBalanceAmount = 10.00;
        double balanceAmountAfterRollback = 20.00;

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

    @Test
    void should_not_allow_rollback_when_payment_is_not_rollbackable() {
        // given
        long accountId = 1L;
        double price = 10.00;
        double currentBalanceAmount = 10.00;
        double balanceAmountAfterRollback = 20.00;

        Balance currentBalance = Balance.builder()
                .id(1L)
                .accountId(accountId)
                .amount(new BigDecimal(currentBalanceAmount))
                .build();

        Payment existingPaymentToRollback = Payment.builder()
                .id(1L)
                .accountId(accountId)
                .accountId(accountId)
                .price(new BigDecimal(price))
                .referenceCode("ref1")
                .state(PaymentState.ROLLBACKED)
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

        assertThatExceptionOfType(PaymentApiBusinessException.class)
                .isThrownBy(() -> paymentRollbackCommandHandler.handle(paymentRollback))
                .withMessage("paymentapi.payment.notRollbackable");
    }

    @Test
    void should_not_allow_rollback_when_payment_is_not_available() {
        // given
        long accountId = 1L;
        double price = 10.00;
        double currentBalanceAmount = 10.00;

        Balance currentBalance = Balance.builder()
                .id(1L)
                .accountId(accountId)
                .amount(new BigDecimal(currentBalanceAmount))
                .build();

        Payment someExistingPaymentToRollback = Payment.builder()
                .id(1L)
                .accountId(accountId)
                .accountId(accountId)
                .price(new BigDecimal(price))
                .referenceCode("ref1")
                .state(PaymentState.ROLLBACKED)
                .build();

        // and
        BalanceFakeAdapter balancePort = new BalanceFakeAdapter(currentBalance, currentBalance);
        PaymentFakeAdapter paymentPort = new PaymentFakeAdapter(null); // payment not found

        BalanceCompensateCommandHandler balanceCompensateCommandHandler = new BalanceCompensateCommandHandler(balancePort);
        PaymentRollbackCommandHandler paymentRollbackCommandHandler = new PaymentRollbackCommandHandler(balanceCompensateCommandHandler,
                new AccountFacade(retrieveFakeAccountLockPort()), paymentPort);

        //when
        PaymentRollback paymentRollback = PaymentRollback.builder()
                .id(1L)
                .accountId(someExistingPaymentToRollback.getAccountId())
                .price(someExistingPaymentToRollback.getPrice())
                .build();

        assertThatExceptionOfType(PaymentApiBusinessException.class)
                .isThrownBy(() -> paymentRollbackCommandHandler.handle(paymentRollback))
                .withMessage("paymentapi.payment.notRollbackable");
    }

    @Test
    void should_not_allow_rollback_when_rollback_is_failed() {
        // given
        long accountId = 6661L;
        double price = 10.00;
        double currentBalanceAmount = 10.00;
        double balanceAmountAfterRollback = 10.00;

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
                .isEqualTo(existingPaymentToRollback.withState(PaymentState.SUCCESS));
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
