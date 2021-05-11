package com.hexagonaldemo.ticketapi.meetup.port;

import com.hexagonaldemo.ticketapi.meetup.model.Meetup;

public interface MeetupDataPort {

    Meetup retrieve(Long id);
}
