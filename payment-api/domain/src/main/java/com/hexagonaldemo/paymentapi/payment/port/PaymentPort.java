package com.hexagonaldemo.paymentapi.payment.port;

import com.hexagonaldemo.paymentapi.payment.usecase.PaymentCreate;
import com.hexagonaldemo.paymentapi.payment.usecase.PaymentRollback;
import com.hexagonaldemo.paymentapi.payment.model.Payment;

public interface PaymentPort {

    Payment create(PaymentCreate paymentCreate);

    Payment retrieve(Long accountId);

    void rollback(PaymentRollback paymentRollback);
}
