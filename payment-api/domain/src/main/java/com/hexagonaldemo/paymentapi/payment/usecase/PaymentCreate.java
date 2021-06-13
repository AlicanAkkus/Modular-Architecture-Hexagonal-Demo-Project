package com.hexagonaldemo.paymentapi.payment.usecase;

import com.hexagonaldemo.paymentapi.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentCreate implements UseCase {

    private Long accountId;
    private BigDecimal price;
    private String referenceCode;
}