package com.hexagonaldemo.paymentapi.payment.port;

import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;

public interface PaymentDataPort {

    Payment create(PaymentCreate paymentCreate);
}
