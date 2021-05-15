package com.hexagonaldemo.paymentapi.integration;

import com.hexagonaldemo.paymentapi.AbstractIT;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentCreateRequest;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentResponse;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "classpath:sql/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class PaymentControllerIT extends AbstractIT {

    @Autowired
    private BalancePort balancePort;

    @Test
    void should_create_payment_when_balance_is_sufficient() {
        doPayment(1L, "10.00", "10.00");
        doPayment(1L, "5.50", "4.50");
        doPayment(1L, "5.00", "4.50", HttpStatus.UNPROCESSABLE_ENTITY, "12");
        doPayment(1L, "4.50", "0.00");
    }

    private void doPayment(Long accountId, String price, String lastBalance) {
        doPayment(accountId, price, lastBalance, HttpStatus.CREATED, null);
    }

    private void doPayment(Long accountId, String price, String lastBalance, HttpStatus httpStatus, String errorCode) {
        // given
        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .accountId(accountId)
                .price(new BigDecimal(price))
                .referenceCode("ref1")
                .build();

        //when
        ResponseEntity<Response<PaymentResponse>> response = testRestTemplate.exchange(
                "/api/v1/payments/",
                HttpMethod.POST,
                new HttpEntity<>(paymentCreateRequest),
                paymentResponseType);

        //then
        Balance balance = balancePort.retrieve(accountId);
        assertThat(balance.getAmount()).isEqualTo(new BigDecimal(lastBalance));

        //and
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(httpStatus);
        assertThat(response.getBody()).isNotNull();

        if (!httpStatus.is2xxSuccessful()) {
            assertThat(response.getBody().getErrors()).isNotNull();
            assertThat(response.getBody().getErrors().getErrorCode()).isEqualTo(errorCode);
            return;
        }

        assertThat(response.getBody().getData()).isNotNull();
        PaymentResponse paymentResponse = response.getBody().getData();
        assertThat(paymentResponse)
                .extracting("accountId", "price", "referenceCode")
                .contains(accountId, new BigDecimal(price), "ref1");
    }

    private final ParameterizedTypeReference<Response<PaymentResponse>> paymentResponseType = new ParameterizedTypeReference<>() {
    };
}
