package com.hexagonaldemo.ticketapi.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.payment.rest.PaymentRestAdapter;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiBusinessException;
import com.hexagonaldemo.ticketapi.payment.command.PaymentCreate;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@IT
class PaymentRestAdapterIT extends AbstractIT {

    private static final WireMockServer wireMockServer = new WireMockServer(9780);

    @Autowired
    PaymentRestAdapter paymentRestAdapter;

    @BeforeAll
    static void setup() {
        wireMockServer.start();

        wireMockServer.stubFor(
                post(urlMatching("/payments"))
                        .withRequestBody(containing("1001"))
                        .willReturn(aResponse()
                                .withBodyFile("payment.json")
                                .withHeader("Content-Type", "application/json")
                                .withStatus(HttpStatus.OK.value())
                        )
        );

        wireMockServer.stubFor(
                post(urlMatching("/payments"))
                        .withRequestBody(containing("6661"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        )
        );

        wireMockServer.stubFor(
                post(urlMatching("/payments"))
                        .withRequestBody(containing("6662"))
                        .willReturn(aResponse()
                                .withBody((String) null)
                                .withHeader("Content-Type", "application/json")
                                .withStatus(HttpStatus.OK.value())
                        )
        );
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    void should_call_payment_api() {
        // given
        PaymentCreate paymentCreate = PaymentCreate.builder()
                .accountId(1001L)
                .price(BigDecimal.valueOf(100.0))
                .referenceCode("test ref code")
                .build();

        Payment expectedPayment = Payment.builder()
                .id(1L)
                .accountId(1001L)
                .price(BigDecimal.valueOf(10.0))
                .referenceCode("ref")
                .build();

        // when
        var createdPayment = paymentRestAdapter.pay(paymentCreate);

        // then
        assertThat(createdPayment).isEqualTo(expectedPayment);
    }

    @Test
    void should_call_payment_api_fails() {
        // given
        PaymentCreate paymentCreate = PaymentCreate.builder()
                .accountId(6661L)
                .price(BigDecimal.valueOf(100.0))
                .referenceCode("test ref code")
                .build();

        // when
        assertThatExceptionOfType(TicketApiBusinessException.class)
                .isThrownBy(() -> paymentRestAdapter.pay(paymentCreate))
                .withMessage("ticketapi.payment.client.error");
    }

    @Test
    void should_call_payment_api_fails_with_empty_response() {
        // given
        PaymentCreate paymentCreate = PaymentCreate.builder()
                .accountId(6662L)
                .price(BigDecimal.valueOf(100.0))
                .referenceCode("test ref code")
                .build();

        // when
        assertThatExceptionOfType(TicketApiBusinessException.class)
                .isThrownBy(() -> paymentRestAdapter.pay(paymentCreate))
                .withMessage("ticketapi.payment.emptyResponse");
    }
}
