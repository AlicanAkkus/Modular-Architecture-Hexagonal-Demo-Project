package com.hexagonaldemo.ticketapi.meetup;

import com.hexagonaldemo.ticketapi.common.DomainComponent;
import com.hexagonaldemo.ticketapi.common.usecase.UseCaseHandler;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import com.hexagonaldemo.ticketapi.meetup.port.MeetupPort;
import com.hexagonaldemo.ticketapi.meetup.usecase.MeetupCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainComponent
@RequiredArgsConstructor
public class MeetupCreateUseCaseHandler implements UseCaseHandler<Meetup, MeetupCreate> {

    private final MeetupPort meetupPort;

    public Meetup handle(MeetupCreate useCase) {
        return meetupPort.create(useCase);
    }
}
