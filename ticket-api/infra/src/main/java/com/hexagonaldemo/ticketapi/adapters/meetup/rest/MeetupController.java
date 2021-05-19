package com.hexagonaldemo.ticketapi.adapters.meetup.rest;

import com.hexagonaldemo.ticketapi.adapters.meetup.rest.dto.MeetupCreateRequest;
import com.hexagonaldemo.ticketapi.adapters.meetup.rest.dto.MeetupResponse;
import com.hexagonaldemo.ticketapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.ticketapi.common.rest.BaseController;
import com.hexagonaldemo.ticketapi.common.rest.Response;
import com.hexagonaldemo.ticketapi.meetup.command.MeetupCreate;
import com.hexagonaldemo.ticketapi.meetup.model.Meetup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meetups")
public class MeetupController extends BaseController {

    private final CommandHandler<Meetup, MeetupCreate> meetupCreateCommandHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Response<MeetupResponse> createMeetup(@Valid @RequestBody MeetupCreateRequest meetupCreateRequest) {
        var meetup = meetupCreateCommandHandler.handle(meetupCreateRequest.toCommand());
        return respond(MeetupResponse.from(meetup));
    }
}
