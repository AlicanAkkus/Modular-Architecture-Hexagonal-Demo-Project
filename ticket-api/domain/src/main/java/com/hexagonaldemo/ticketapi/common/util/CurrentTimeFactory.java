package com.hexagonaldemo.ticketapi.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CurrentTimeFactory {

    private static LocalDateTime customLocalDateTime;

    public static LocalDateTime now() {
        return customLocalDateTime == null ? LocalDateTime.now() : customLocalDateTime;
    }

    public static LocalDate today() {
        return customLocalDateTime == null ? LocalDateTime.now().toLocalDate() : customLocalDateTime.toLocalDate();
    }

    public static LocalTime moment() {
        return customLocalDateTime == null ? LocalDateTime.now().toLocalTime() : customLocalDateTime.toLocalTime();
    }

    public static void setCustom(LocalDateTime dateTime) {
        customLocalDateTime = dateTime;
    }

    public static void setCustom(LocalTime time) {
        customLocalDateTime = LocalDateTime.of(LocalDate.now(), time);
    }

    public static void reset() {
        customLocalDateTime = null;
    }
}
