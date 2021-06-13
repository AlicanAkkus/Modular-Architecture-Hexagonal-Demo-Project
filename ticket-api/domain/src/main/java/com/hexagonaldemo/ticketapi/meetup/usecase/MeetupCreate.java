package com.hexagonaldemo.ticketapi.meetup.usecase;

import com.hexagonaldemo.ticketapi.common.model.UseCase;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class MeetupCreate implements UseCase {

    private String name;
    private String website;
    private BigDecimal price;
    private LocalDateTime eventDate;
}
