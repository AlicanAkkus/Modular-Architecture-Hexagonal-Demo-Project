package com.hexagonaldemo.ticketapi.notification;

public interface NotificationPublisher<T> {

    void publish(T model);
}
