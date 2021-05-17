package com.hexagonaldemo.paymentapi.common.model;

public interface Event<T> {
    T toModel();
}
