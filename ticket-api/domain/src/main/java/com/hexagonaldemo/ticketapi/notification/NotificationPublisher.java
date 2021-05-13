package com.hexagonaldemo.ticketapi.notification;

import org.springframework.stereotype.Component;

public interface NotificationPublisher<T> {

    void publish(T model);
}
