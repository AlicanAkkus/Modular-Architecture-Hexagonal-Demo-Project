package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.reservation.rest.dto.ReserveTicketRequest;
import com.hexagonaldemo.ticketapi.adapters.reservation.rest.dto.ReserveTicketResponse;
import com.hexagonaldemo.ticketapi.adapters.ticket.rest.dto.TicketResponse;
import com.hexagonaldemo.ticketapi.common.rest.DataResponse;
import com.hexagonaldemo.ticketapi.common.rest.ErrorResponse;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
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
class TicketControllerIT extends AbstractIT {

    private final ParameterizedTypeReference<Response<DataResponse<TicketResponse>>> retrieveTicketResponseType = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_retrieve_tickets_for_account() {
        // given
        long accountId = 10L;

        // when
        var responseEntity = testRestTemplate.exchange("/api/v1/tickets?accountId=" + accountId,
                HttpMethod.GET,
                new HttpEntity<>(null, null), retrieveTicketResponseType);

        // then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.OK, from(ResponseEntity::getStatusCode));

        // then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull();
        assertThat(responseEntity.getBody().getData().getItems()).isNotNull().hasSize(3)
                .extracting("accountId", "meetupId", "price")
                .containsExactly(
                        tuple(10L, 1001L, BigDecimal.valueOf(100.00)),
                        tuple(10L, 1002L, BigDecimal.valueOf(110.00)),
                        tuple(10L, 1003L, BigDecimal.valueOf(120.00))
                );
    }

    @Test
    void should_retrieve_no_tickets_for_account() {
        // given
        long accountId = 6661L;

        // when
        var responseEntity = testRestTemplate.exchange("/api/v1/tickets?accountId=" + accountId,
                HttpMethod.GET,
                new HttpEntity<>(null, null), retrieveTicketResponseType);

        // then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.OK, from(ResponseEntity::getStatusCode));

        // then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull();
        assertThat(responseEntity.getBody().getData().getItems()).isNotNull().isEmpty();
    }
}
