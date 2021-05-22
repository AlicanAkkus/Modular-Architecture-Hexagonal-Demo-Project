package com.hexagonaldemo.paymentapi;

import com.hexagonaldemo.paymentapi.adapters.MeetupFakeDataAdapter;
import com.hexagonaldemo.ticketapi.meetup.MeetupCreateCommandHandler;
import com.hexagonaldemo.ticketapi.meetup.command.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MeetupCreateTest {

    MeetupCreateCommandHandler meetupCreateCommandHandler;

    @BeforeEach
    void setUp() {
        meetupCreateCommandHandler = new MeetupCreateCommandHandler(new MeetupFakeDataAdapter());
    }

    @Test
    void should_create_meetup() {
        // given
        MeetupCreate meetupCreate = MeetupCreate.builder()
                .name("test meetup")
                .website("test meetup website")
                .price(BigDecimal.valueOf(50.0))
                .eventDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                .build();

        // when
        var meetup = meetupCreateCommandHandler.handle(meetupCreate);

        // then
        assertThat(meetup).isNotNull()
                .returns(1L, Meetup::getId)
                .returns("test meetup", Meetup::getName)
                .returns("test meetup website", Meetup::getWebsite)
                .returns(BigDecimal.valueOf(50.0), Meetup::getPrice)
                .returns(LocalDateTime.of(2021, 1, 1, 19, 0, 0), Meetup::getEventDate);
    }

}
