package com.hexagonaldemo.ticketapi.meetup;

import com.hexagonaldemo.ticketapi.common.commandhandler.VoidEmptyCommandHandler;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service("meetupAdminCommandHandler")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "administration.enabled", havingValue = "true")
public class MeetupAdminCommandHandler implements VoidEmptyCommandHandler {

    private final MeetupPort meetupPort;

    public void handle() {
        meetupPort.deleteAll();
    }
}
