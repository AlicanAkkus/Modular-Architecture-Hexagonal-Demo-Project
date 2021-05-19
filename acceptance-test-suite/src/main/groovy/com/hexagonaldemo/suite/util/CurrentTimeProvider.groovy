package com.hexagonaldemo.suite.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class CurrentTimeProvider {

    private static LocalDateTime customLocalDateTime

    static LocalDateTime now() {
        return customLocalDateTime == null ? LocalDateTime.now() : customLocalDateTime
    }

    static LocalDate today() {
        return customLocalDateTime == null ? LocalDateTime.now().toLocalDate() : customLocalDateTime.toLocalDate()
    }

    static LocalTime moment() {
        return customLocalDateTime == null ? LocalDateTime.now().toLocalTime() : customLocalDateTime.toLocalTime()
    }

    static void setCustomLocalDateTime(LocalDateTime dateTime) {
        customLocalDateTime = dateTime
    }

    static void setCustomLocalDateTime(LocalTime time) {
        customLocalDateTime = LocalDateTime.of(LocalDate.now(), time)
    }

    static void resetCustomLocalDateTime() {
        customLocalDateTime = null
    }

    static String toString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
