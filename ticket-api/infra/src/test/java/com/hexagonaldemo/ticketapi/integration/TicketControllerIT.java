package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto.BuyTicketRequest;
import com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto.TicketResponse;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@IT
public class TicketControllerIT extends AbstractIT {

    private final ParameterizedTypeReference<Response<TicketResponse>> responseType = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_buy_ticket() {
        //given
        BuyTicketRequest buyTicketRequest = BuyTicketRequest.builder()
                .count(3)
                .meetupId(100L)
                .accountId(2021L)
                .referenceCode("ref2021")
                .build();

        //when
        ResponseEntity<Response<TicketResponse>> responseEntity = testRestTemplate.exchange("/api/v1/tickets", HttpMethod.POST, new HttpEntity<>(buyTicketRequest), responseType);

        //then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.CREATED, from(ResponseEntity::getStatusCode));

        //then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull()
                .returns(3, from(TicketResponse::getCount))
                .returns(100L, from(TicketResponse::getMeetupId))
                .returns(2021L, from(TicketResponse::getAccountId))
                .returns(true, from(r -> r.getReserveDate().isAfter(LocalDateTime.now().minusMinutes(1))));

        //then - assert event
        ticketEventKafkaStreamConsumer.wait(3,1);
        var events = ticketEventKafkaStreamConsumer.popAll();

        assertThat(events).hasSize(1)
                .extracting("accountId", "meetupId", "price")
                .containsExactly(
                        tuple(2021L, 100L, BigDecimal.valueOf(359.97))
                );
    }
}
