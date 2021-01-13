package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.port.PaymentDataPort;

public class PaymentFakeDataAdapter implements PaymentDataPort {

    private final Payment payment;

    public PaymentFakeDataAdapter(Payment payment) {
        this.payment = payment;
    }

    @Override
    public Payment create(PaymentCreate paymentCreate) {
        return payment;
    }
}
