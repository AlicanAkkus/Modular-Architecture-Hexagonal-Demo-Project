package com.hexagonaldemo.ticketapi.external;

import com.hexagonaldemo.ticketapi.adapters.payment.rest.PaymentRestAdapter;
import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("stubTest")
@DirtiesContext
@AutoConfigureStubRunner
@TestPropertySource("classpath:payment-contract.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentApiExternalContractIT {

    @Autowired
    private PaymentRestAdapter paymentRestAdapter;

    @Test
    void should_pay() {
        //given
        CreatePayment createPayment = CreatePayment.builder()
                .accountId(1L)
                .price(BigDecimal.TEN)
                .referenceCode("ref1")
                .build();

        //when
        Payment payment = paymentRestAdapter.pay(createPayment);

        //then
        assertThat(payment).isNotNull();
        assertThat(payment).extracting("id", "accountId", "price", "referenceCode")
                .contains(1L, 1L, new BigDecimal("10.00"), "ref1");
    }
}
