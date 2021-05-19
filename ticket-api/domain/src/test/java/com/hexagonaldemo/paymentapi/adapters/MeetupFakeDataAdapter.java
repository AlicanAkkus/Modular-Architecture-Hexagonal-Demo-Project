package com.hexagonaldemo.paymentapi.adapters;

import com.hexagonaldemo.ticketapi.meetup.command.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MeetupFakeDataAdapter implements MeetupPort {

    @Override
    public Meetup retrieve(Long id) {
        return Meetup.builder()
                .id(id)
                .price(BigDecimal.valueOf(100L))
                .eventDate(LocalDateTime.of(2021, 1, 1, 20, 0, 0))
                .name("test meetup")
                .website("testmeetup.com")
                .build();
    }

    @Override
    public Meetup create(MeetupCreate meetupCreate) {
        return Meetup.builder()
                .id(1L)
                .name(meetupCreate.getName())
                .website(meetupCreate.getWebsite())
                .price(meetupCreate.getPrice())
                .eventDate(meetupCreate.getEventDate())
                .build();
    }

    @Override
    public void deleteAll() {

    }
}
