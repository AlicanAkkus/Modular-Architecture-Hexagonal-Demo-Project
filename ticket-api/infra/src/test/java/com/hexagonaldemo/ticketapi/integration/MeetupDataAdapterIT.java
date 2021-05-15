package com.hexagonaldemo.ticketapi.integration;

import com.hexagonaldemo.ticketapi.AbstractIT;
import com.hexagonaldemo.ticketapi.IT;
import com.hexagonaldemo.ticketapi.adapters.meetup.jpa.MeetupDataAdapter;
import com.hexagonaldemo.ticketapi.adapters.meetup.jpa.repository.MeetupJpaRepository;
import com.hexagonaldemo.ticketapi.common.exception.TicketApiDataNotFoundException;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

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
        // given
        // when
        assertThatExceptionOfType(TicketApiDataNotFoundException.class)
                .isThrownBy(() -> meetupAdapter.retrieve(666L))
                .withMessage("ticketapi.meetup.notFound");
    }
}
