package com.hexagonaldemo.ticketapi.meetup;

import com.hexagonaldemo.ticketapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.ticketapi.meetup.command.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "true")
public class MeetupCreateCommandHandler implements CommandHandler<Meetup, MeetupCreate> {

    private final MeetupPort meetupPort;

    public Meetup handle(MeetupCreate meetupCreate) {
        return meetupPort.create(meetupCreate);
    }
}
