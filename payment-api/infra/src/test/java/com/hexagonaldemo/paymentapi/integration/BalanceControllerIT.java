package com.hexagonaldemo.paymentapi.integration;

import com.hexagonaldemo.paymentapi.AbstractIT;
import com.hexagonaldemo.paymentapi.adapters.balance.rest.dto.BalanceResponse;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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
}
