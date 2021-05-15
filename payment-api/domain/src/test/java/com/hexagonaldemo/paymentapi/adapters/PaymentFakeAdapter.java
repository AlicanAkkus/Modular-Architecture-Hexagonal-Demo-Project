package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.port.PaymentPort;

public class PaymentFakeAdapter implements PaymentPort {

    private final Payment payment;

    public PaymentFakeAdapter(Payment payment) {
        this.payment = payment;
    }

    @Override
    public Payment create(PaymentCreate paymentCreate) {
        return payment;
    }
}
