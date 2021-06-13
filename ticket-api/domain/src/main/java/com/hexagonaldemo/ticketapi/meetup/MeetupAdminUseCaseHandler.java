package com.hexagonaldemo.ticketapi.meetup;

import com.hexagonaldemo.ticketapi.common.DomainComponent;
import com.hexagonaldemo.ticketapi.common.usecase.VoidEmptyUseCaseHandler;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class MeetupAdminUseCaseHandler implements VoidEmptyUseCaseHandler {

    private final MeetupPort meetupPort;

    public void handle() {
        meetupPort.deleteAll();
    }
}
