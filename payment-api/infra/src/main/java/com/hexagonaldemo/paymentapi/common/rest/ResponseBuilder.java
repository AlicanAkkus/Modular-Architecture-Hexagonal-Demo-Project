package com.hexagonaldemo.paymentapi.common.rest;

import java.util.List;

public class ResponseBuilder {

    private ResponseBuilder() {
    }

    public static <T> Response<DataResponse<T>> build(List<T> items) {
        return new Response<>(new DataResponse<>(items));
    }

    public static <T> Response<DataResponse<T>> build(List<T> items, Integer page, Integer size, Long totalSize) {
        return new Response<>(new DataResponse<>(items, page, size, totalSize));
    }

    public static <T> Response<T> build(T item) {
        return new Response<>(item);
    }

    public static Response<ErrorResponse> build(ErrorResponse errorResponse) {
        return new Response<>(errorResponse);
    }
}