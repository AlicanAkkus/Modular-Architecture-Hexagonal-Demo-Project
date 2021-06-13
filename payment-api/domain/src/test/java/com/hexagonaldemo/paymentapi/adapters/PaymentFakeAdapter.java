package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.paymentapi.payment.usecase.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.usecase.PaymentRollback;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.model.PaymentState;
import com.hexagonaldemo.paymentapi.payment.port.PaymentPort;

public class PaymentFakeAdapter implements PaymentPort {

    private static final Long DISABLED_PAYMENT_ROLLBACK_ACCOUNT_ID = 6661L;

    private final Payment payment;

    public PaymentFakeAdapter(Payment payment) {
        this.payment = payment;
    }

    @Override
    public Payment create(PaymentCreate paymentCreate) {
        return payment;
    }

    @Override
    public Payment retrieve(Long id) {
        return payment;
    }

    @Override
    public void rollback(PaymentRollback paymentRollback) {
        if (paymentRollback.getAccountId().equals(DISABLED_PAYMENT_ROLLBACK_ACCOUNT_ID)) return;
        payment.setState(PaymentState.ROLLBACKED);
    }
}
