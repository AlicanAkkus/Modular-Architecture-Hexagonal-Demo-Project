package com.hexagonaldemo.paymentapi.integration;

import com.hexagonaldemo.paymentapi.AbstractIT;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentCreateRequest;
import com.hexagonaldemo.paymentapi.adapters.payment.rest.dto.PaymentResponse;
import com.hexagonaldemo.paymentapi.common.rest.Response;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentControllerIT extends AbstractIT {

    private final ParameterizedTypeReference<Response<PaymentResponse>> paymentResponseType = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_create_payment_when_balance_is_sufficient() {
        doPayment(1L, "10.0", "10.0");
        doPayment(2L, "10.0", "20.0");
        doPayment(6661L, "5.0", "4.5", HttpStatus.UNPROCESSABLE_ENTITY, "12");
        doPayment(6661L, "4.0", "0.0", HttpStatus.UNPROCESSABLE_ENTITY, "12");
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
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(httpStatus);
        assertThat(response.getBody()).isNotNull();

        if (hasSufficientBalance(price, lastBalance)) {
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

    private boolean hasSufficientBalance(String price, String lastBalance) {
        return new BigDecimal(lastBalance).doubleValue() < new BigDecimal(price).doubleValue();
    }
}
