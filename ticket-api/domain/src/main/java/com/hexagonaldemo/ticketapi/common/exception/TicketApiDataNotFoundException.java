package com.hexagonaldemo.ticketapi.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketApiDataNotFoundException extends RuntimeException {

    private String key;
    private final String[] args;

    public TicketApiDataNotFoundException(String key) {
        super(key);
        this.key = key;
        args = new String[0];
    }

    public TicketApiDataNotFoundException(String key, String... args) {
        super(key);
        this.key = key;
        this.args = args;
    }
}