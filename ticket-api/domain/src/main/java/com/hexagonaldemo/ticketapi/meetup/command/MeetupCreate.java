package com.hexagonaldemo.ticketapi.meetup.command;

import com.hexagonaldemo.ticketapi.common.model.Command;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MeetupCreate implements Command {

    private String name;
    private String website;
    private BigDecimal price;
    private LocalDateTime eventDate;
}
