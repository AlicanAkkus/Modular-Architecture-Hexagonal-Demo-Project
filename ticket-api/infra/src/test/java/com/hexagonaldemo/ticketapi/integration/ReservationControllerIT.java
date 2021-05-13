package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.reservation.rest.dto.ReserveTicketRequest;
import com.hexagonaldemo.ticketapi.adapters.reservation.rest.dto.ReserveTicketResponse;
import com.hexagonaldemo.ticketapi.common.rest.ErrorResponse;
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
public class ReservationControllerIT extends AbstractIT {

    private final ParameterizedTypeReference<Response<ReserveTicketResponse>> responseType = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_reserve_ticket() {
        //given
        ReserveTicketRequest reserveTicketRequest = ReserveTicketRequest.builder()
                .count(3)
                .meetupId(100L)
                .accountId(2021L)
                .referenceCode("ref2021")
                .build();

        //when
        ResponseEntity<Response<ReserveTicketResponse>> responseEntity = testRestTemplate.exchange("/api/v1/tickets", HttpMethod.POST, new HttpEntity<>(reserveTicketRequest), responseType);

        //then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.CREATED, from(ResponseEntity::getStatusCode));

        //then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull()
                .returns(1L, from(ReserveTicketResponse::getId))
                .returns(3, from(ReserveTicketResponse::getCount))
                .returns(100L, from(ReserveTicketResponse::getMeetupId))
                .returns(2021L, from(ReserveTicketResponse::getAccountId))
                .returns(BigDecimal.valueOf(100.00), from(ReserveTicketResponse::getPrice))
                .returns(LocalDateTime.of(2021,1,1,19,0,0), from(ReserveTicketResponse::getReserveDate));
    }

    @Test
    void should_not_reserve_ticket_when_payment_fails() {
        //given
        ReserveTicketRequest reserveTicketRequest = ReserveTicketRequest.builder()
                .count(3)
                .meetupId(100L)
                .accountId(6661L)
                .referenceCode("ref2021")
                .build();

        //when
        ResponseEntity<Response<ReserveTicketResponse>> responseEntity = testRestTemplate.exchange("/api/v1/tickets", HttpMethod.POST, new HttpEntity<>(reserveTicketRequest), responseType);

        //then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.UNPROCESSABLE_ENTITY, from(ResponseEntity::getStatusCode));

        //then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getErrors()).isNotNull()
                .returns("12", from(ErrorResponse::getErrorCode));
    }

    @Test
    void should_not_reserve_ticket_when_ticket_create_fails() {
        //given
        ReserveTicketRequest reserveTicketRequest = ReserveTicketRequest.builder()
                .count(3)
                .meetupId(100L)
                .accountId(6662L)
                .referenceCode("ref2021")
                .build();

        //when
        ResponseEntity<Response<ReserveTicketResponse>> responseEntity = testRestTemplate.exchange("/api/v1/tickets", HttpMethod.POST, new HttpEntity<>(reserveTicketRequest), responseType);

        //then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.UNPROCESSABLE_ENTITY, from(ResponseEntity::getStatusCode));

        //then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getErrors()).isNotNull()
                .returns("14", from(ErrorResponse::getErrorCode));
    }
}
