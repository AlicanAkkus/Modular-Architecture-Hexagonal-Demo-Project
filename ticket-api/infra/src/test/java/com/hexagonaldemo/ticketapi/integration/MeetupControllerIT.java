package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.meetup.rest.dto.MeetupResponse;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.meetup.command.MeetupCreate;
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
class MeetupControllerIT extends AbstractIT {

    private final ParameterizedTypeReference<Response<MeetupResponse>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
    };

    @Test
    void should_create_meetup() {
        // given
        MeetupCreate meetupCreate = MeetupCreate.builder()
                .name("test meetup")
                .website("test meetup website")
                .price(BigDecimal.valueOf(50.0))
                .eventDate(LocalDateTime.of(2021,1,1,19,0,0))
                .build();

        // when
        var responseEntity = testRestTemplate.exchange("/api/v1/meetups",
                HttpMethod.POST,
                new HttpEntity<>(meetupCreate, null), parameterizedTypeReference);

        // then - assert response
        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.OK, from(ResponseEntity::getStatusCode));

        // then - assert data
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getData()).isNotNull()
                .returns("test meetup", from(MeetupResponse::getName))
                .returns(BigDecimal.valueOf(50.0), from(MeetupResponse::getPrice));
    }
}
