package com.hexagonaldemo.paymentapi.balance.usecase;

import com.hexagonaldemo.paymentapi.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceRetrieve implements UseCase {

    private Long accountId;

    public static BalanceRetrieve from(Long accountId) {
        return BalanceRetrieve.builder().accountId(accountId).build();
    }
}