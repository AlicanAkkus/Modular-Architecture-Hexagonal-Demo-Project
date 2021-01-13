package com.hexagonaldemo.ticketapi.account.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

    private Long id;
    private String name;
    private String surname;
}
