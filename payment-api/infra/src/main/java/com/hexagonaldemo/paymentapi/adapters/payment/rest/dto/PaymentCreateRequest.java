package com.hexagonaldemo.paymentapi.adapters.payment.rest.dto;

import com.hexagonaldemo.paymentapi.payment.command.PaymentCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateRequest {

    @NotNull
    private Long accountId;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotEmpty
    private String referenceCode;

    public PaymentCreate toModel() {
        return PaymentCreate.builder()
                .price(price)
                .accountId(accountId)
                .referenceCode(referenceCode)
                .build();
    }
}