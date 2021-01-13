package com.hexagonaldemo.paymentapi.payment.command;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentCreate {

    private Long accountId;
    private BigDecimal price;
    private String referenceCode;
}