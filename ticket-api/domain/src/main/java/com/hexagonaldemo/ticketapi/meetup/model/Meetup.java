package com.hexagonaldemo.ticketapi.meetup.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Meetup {

    private Long id;
    private String name;
    private String website;
    private BigDecimal price;
    private LocalDateTime eventDate;
}
