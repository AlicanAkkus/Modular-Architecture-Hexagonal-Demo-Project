package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.payment.rest.PaymentRestAdapter;
import com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto.BuyTicketRequest;
import com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto.TicketResponse;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.payment.command.CreatePayment;
import com.hexagonaldemo.ticketapi.payment.model.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@IT
public class TicketControllerIT extends AbstractIT {

    private final ParameterizedTypeReference<Response<TicketResponse>> responseType = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_buy_ticket() {
        //given
        BuyTicketRequest buyTicketRequest = BuyTicketRequest.builder()
                .count(3)
                .eventId(100L)
                .accountId(2021L)
                .referenceCode("ref2021")
                .build();

        //when
        ResponseEntity<Response<TicketResponse>> responseEntity = testRestTemplate.exchange("/api/v1/tickets", HttpMethod.POST, new HttpEntity(buyTicketRequest), responseType);

        //then
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getData().getAccountId()).isEqualTo(2021L);
        assertThat(responseEntity.getBody().getData().getEventId()).isEqualTo(100L);
        assertThat(responseEntity.getBody().getData().getCount()).isEqualTo(3);
        assertThat(responseEntity.getBody().getData().getBoughtDate().toLocalDate()).isToday();
        assertThat(responseEntity.getBody().getData().getPrice()).isEqualTo(new BigDecimal("119.99").multiply(BigDecimal.valueOf(3)));
    }
}
