package com.hexagonaldemo.paymentapi.payment.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class Payment {

    private Long id;
    private LocalDateTime createdAt;
    private Long accountId;
    private BigDecimal price;
    private String referenceCode;
    private PaymentState state;
}