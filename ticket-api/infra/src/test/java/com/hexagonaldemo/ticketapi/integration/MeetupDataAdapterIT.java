package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.meetup.jpa.MeetupDataAdapter;
import com.hexagonaldemo.ticketapi.adapters.meetup.jpa.repository.MeetupJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.meetup.usecase.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@IT
@Sql(scripts = "classpath:sql/meetups.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class MeetupDataAdapterIT extends AbstractIT {

    @Autowired
    MeetupDataAdapter meetupAdapter;

    @Autowired
    MeetupJpaRepository meetupJpaRepository;

    @Test
    void should_retrieve_meetup() {
        // when
        Meetup meetup = meetupAdapter.retrieve(2001L);

        // then
        assertThat(meetup).isNotNull()
                .returns(2001L, from(Meetup::getId));
    }

    @Test
    void should_not_retrieve_meetup_when_meetup_not_found() {
        // when
        assertThatExceptionOfType(TicketApiDataNotFoundException.class)
                .isThrownBy(() -> meetupAdapter.retrieve(666L))
                .withMessage("ticketapi.meetup.notFound");
    }

    @Test
    void should_create_meetup() {
        // given
        MeetupCreate createMeetup = MeetupCreate.builder()
                .name("test event name")
                .website("test event website")
                .price(BigDecimal.valueOf(50.51))
                .eventDate(LocalDateTime.of(2021,1,1,19,0,0))
                .build();

        // when
        Meetup createdMeetup = meetupAdapter.create(createMeetup);

        // then
        var meetupEntity = meetupJpaRepository.findById(2002L);
        assertThat(meetupEntity).isPresent();
        assertThat(meetupEntity.get().toModel()).isEqualTo(createdMeetup);
    }

    @Test
    void should_delete_all_meetups() {
        // when
        meetupAdapter.deleteAll();

        // then
        var meetupEntities = meetupJpaRepository.findAll();
        assertThat(meetupEntities).isEmpty();
    }
}
