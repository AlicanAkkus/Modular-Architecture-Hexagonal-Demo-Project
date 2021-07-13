package com.hexagonaldemo.paymentapi.common.rest;

import com.hexagonaldemo.paymentapi.common.usecase.BeanAwareUseCasePublisher;

import java.util.List;

public class BaseController extends BeanAwareUseCasePublisher {

    public <T> Response<DataResponse<T>> respond(List<T> items) {
        return ResponseBuilder.build(items);
    }

    public <T> Response<DataResponse<T>> respond(List<T> items, int page, int size, Long totalSize) {
        return ResponseBuilder.build(items, page, size, totalSize);
    }

    protected <T> Response<T> respond(T item) {
        return ResponseBuilder.build(item);
    }

    protected Response<ErrorResponse> respond(ErrorResponse errorResponse) {
        return ResponseBuilder.build(errorResponse);
    }
}