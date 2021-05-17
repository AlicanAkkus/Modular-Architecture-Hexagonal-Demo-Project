package com.hexagonaldemo.ticketapi.adapters.payment.rest;

import com.hexagonaldemo.ticketapi.adapters.payment.rest.dto.PaymentCreateRequest;
import com.hexagonaldemo.ticketapi.adapters.payment.rest.dto.PaymentResponse;
import com.hexagonaldemo.ticketapi.adapters.payment.rest.properties.PaymentApiProperties;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.payment.command.PaymentCreate;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.springframework.http.HttpMethod.POST;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.payment.enabled", havingValue = "true")
public class PaymentRestAdapter implements PaymentPort {

    private final RestTemplate restTemplate;
    private final PaymentApiProperties paymentApiProperties;
    private final ParameterizedTypeReference<Response<PaymentResponse>> paymentResponseType = new ParameterizedTypeReference<>() {
    };

    @Override
    @Retryable(value = {Exception.class},
            maxAttemptsExpression = "${adapters.payment.retryAttempts}",
            backoff = @Backoff(delayExpression = "${adapters.payment.retryDelay}"))
    public Payment pay(PaymentCreate paymentCreate) {

        var paymentCreateRequest = PaymentCreateRequest.builder()
                .accountId(paymentCreate.getAccountId())
                .price(paymentCreate.getPrice())
                .referenceCode(paymentCreate.getReferenceCode())
                .build();

        var response = callApi(paymentCreateRequest, preparePaymentUrl());

        return Payment.builder()
                .id(response.getId())
                .accountId(response.getAccountId())
                .price(response.getPrice())
                .referenceCode(response.getReferenceCode())
                .build();
    }

    @Recover
    public Payment pay(TicketApiBusinessException e, PaymentCreate paymentCreate) {
        log.error("Couldn't connect to payment api to do payment for {}", paymentCreate, e);
        throw e;
    }

    @Recover
    public Payment pay(Exception e, PaymentCreate paymentCreate) {
        log.error("Couldn't connect to payment api to do payment for {}", paymentCreate, e);
        throw new TicketApiBusinessException("ticketapi.payment.client.error");
    }

    private PaymentResponse callApi(PaymentCreateRequest paymentCreateRequest, String url) {
        var exchange = restTemplate.exchange(url, POST, new HttpEntity<>(paymentCreateRequest), this.paymentResponseType);
        var body = exchange.getBody();

        if (Objects.isNull(body)) throw new TicketApiBusinessException("ticketapi.payment.emptyResponse");
        return body.getData();
    }

    private String preparePaymentUrl() {
        return paymentApiProperties.getBaseUrl() + paymentApiProperties.getPaymentPath();
    }
}
