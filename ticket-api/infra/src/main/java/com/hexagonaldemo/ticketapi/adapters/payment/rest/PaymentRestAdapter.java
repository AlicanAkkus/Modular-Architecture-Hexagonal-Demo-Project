package com.hexagonaldemo.ticketapi.adapters.payment.rest;

import com.hexagonaldemo.ticketapi.adapters.payment.rest.dto.PaymentCreateRequest;
import com.hexagonaldemo.ticketapi.adapters.payment.rest.dto.PaymentResponse;
import com.hexagonaldemo.ticketapi.adapters.payment.rest.properties.PaymentApiProperties;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import com.hexagonaldemo.ticketapi.payment.port.PaymentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.springframework.http.HttpMethod.POST;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "adapters.payment.enabled", havingValue = "true")
public class PaymentRestAdapter implements PaymentPort {

    private final RestTemplate restTemplate;
    private final PaymentApiProperties paymentApiProperties;
    private final ParameterizedTypeReference<Response<PaymentResponse>> paymentResponseType = new ParameterizedTypeReference<Response<PaymentResponse>>() {
    };

    @Override
    public Payment pay(CreatePayment createPayment) {
        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .accountId(createPayment.getAccountId())
                .price(createPayment.getPrice())
                .referenceCode(createPayment.getReferenceCode())
                .build();

        String url = preparePaymentUrl();

        var exchange = restTemplate.exchange(url, POST, new HttpEntity<>(paymentCreateRequest), this.paymentResponseType);
        Response<PaymentResponse> body = exchange.getBody();

        if (Objects.isNull(body)) throw new TicketApiBusinessException("ticketapi.payment.emptyResponse");
        PaymentResponse paymentResponse = body.getData();

        return Payment.builder()
                .id(paymentResponse.getId())
                .accountId(paymentResponse.getAccountId())
                .price(paymentResponse.getPrice())
                .referenceCode(paymentResponse.getReferenceCode())
                .build();
    }

    private String preparePaymentUrl() {
        return paymentApiProperties.getBaseUrl() + paymentApiProperties.getPaymentPath();
    }
}
