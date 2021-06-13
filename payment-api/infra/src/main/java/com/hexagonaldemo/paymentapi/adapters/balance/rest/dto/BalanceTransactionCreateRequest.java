package com.hexagonaldemo.paymentapi.adapters.balance.rest.dto;

import com.hexagonaldemo.paymentapi.balance.usecase.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceTransactionCreateRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private BalanceTransactionType type;

    public BalanceTransactionCreate toUseCase() {
        return BalanceTransactionCreate.builder()
                .accountId(accountId)
                .amount(amount)
                .type(type)
                .build();
    }
}