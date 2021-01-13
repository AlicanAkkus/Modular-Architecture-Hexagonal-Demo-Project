package com.hexagonaldemo.ticketapi.common.model;

import java.util.stream.Stream;

public enum Status {

    ACTIVE(1),
    PASSIVE(0),
    DELETED(-1);

    private final Integer value;

    Status(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Status of(Integer value) {
        return Stream.of(Status.values())
                .filter(status -> status.value.equals(value)).findFirst().orElseThrow();
    }
}
