package com.hexagonaldemo.paymentapi.integration;

import com.hexagonaldemo.paymentapi.AbstractIT;
import com.hexagonaldemo.paymentapi.IT;
import com.hexagonaldemo.paymentapi.adapters.balance.rest.dto.BalanceResponse;
import com.hexagonaldemo.paymentapi.adapters.balance.rest.dto.BalanceTransactionCreateRequest;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@IT
class BalanceControllerIT extends AbstractIT {

    private final ParameterizedTypeReference<Response<BalanceResponse>> balanceResponseType = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_retrieve_balance_by_accountId() {
        // given
        Long accountId = 1L;

        //when
        ResponseEntity<Response<BalanceResponse>> response = testRestTemplate.exchange(
                "/api/v1/balances?accountId=" + accountId,
                HttpMethod.GET, new HttpEntity<>(null, null), balanceResponseType);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().getData()).isNotNull();
        BalanceResponse balanceResponse = response.getBody().getData();
        assertThat(balanceResponse)
                .extracting("accountId", "amount", "id")
                .contains(accountId, BigDecimal.valueOf(10.0), 1L);
    }


    @Test
    void should_not_retrieve_balance_when_balance_not_found() {
        // given
        Long accountId = 6661L;

        //when
        ResponseEntity<Response<BalanceResponse>> response = testRestTemplate.exchange(
                "/api/v1/balances?accountId=" + accountId,
                HttpMethod.GET, new HttpEntity<>(null, null), balanceResponseType);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getErrors()).isNotNull();
        assertThat(response.getBody().getErrors().getErrorCode()).isEqualTo("11");
    }

    @Test
    void should_deposit_withdraw_balance() {
        updateBalance(1L, BalanceTransactionType.DEPOSIT, 50.0, 50.0);
        updateBalance(1L, BalanceTransactionType.DEPOSIT, 25.0, 75.0);
        updateBalance(1L, BalanceTransactionType.WITHDRAW, 10.0, 65.0);
        updateBalance(1L, BalanceTransactionType.COMPENSATE, 100.0, 165.0);
    }

    private void updateBalance(Long accountId, BalanceTransactionType balanceTransactionType, double amount, double expectedAmount) {
        // given
        BalanceTransactionCreateRequest balanceTransactionCreateRequest = BalanceTransactionCreateRequest.builder()
                .accountId(accountId)
                .type(balanceTransactionType)
                .amount(BigDecimal.valueOf(amount))
                .build();

        //when
        ResponseEntity<Response<BalanceResponse>> response1 = testRestTemplate.exchange(
                "/api/v1/balances",
                HttpMethod.POST, new HttpEntity<>(balanceTransactionCreateRequest, null), balanceResponseType);

        //then
        assertThat(response1).isNotNull();
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody()).isNotNull();

        assertThat(response1.getBody().getData()).isNotNull();
        BalanceResponse balanceResponse1 = response1.getBody().getData();
        assertThat(balanceResponse1)
                .extracting("accountId", "amount")
                .contains(accountId, BigDecimal.valueOf(expectedAmount));
    }
}
