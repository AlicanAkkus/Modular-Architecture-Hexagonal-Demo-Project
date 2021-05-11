package com.hexagonaldemo.ticketapi.payment.port;

import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.model.Payment;

public interface PaymentApiPort {

    Payment pay(CreatePayment createPayment);
}
