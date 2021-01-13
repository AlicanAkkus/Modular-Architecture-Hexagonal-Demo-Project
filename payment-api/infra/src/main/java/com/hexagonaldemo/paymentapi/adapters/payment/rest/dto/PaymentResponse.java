package com.hexagonaldemo.paymentapi.adapters.payment.rest.dto;

import com.hexagonaldemo.paymentapi.payment.model.Payment;
import com.hexagonaldemo.paymentapi.payment.model.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private Long accountId;
    private BigDecimal price;
    private String referenceCode;

    public static PaymentResponse fromModel(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .price(payment.getPrice())
                .accountId(payment.getAccountId())
                .referenceCode(payment.getReferenceCode())
                .build();
    }
}