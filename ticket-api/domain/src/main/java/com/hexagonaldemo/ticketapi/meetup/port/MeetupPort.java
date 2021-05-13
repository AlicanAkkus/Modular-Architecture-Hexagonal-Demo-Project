package com.hexagonaldemo.ticketapi.meetup.port;

import com.hexagonaldemo.ticketapi.meetup.model.Meetup;

public interface MeetupPort {

    Meetup retrieve(Long id);
}
