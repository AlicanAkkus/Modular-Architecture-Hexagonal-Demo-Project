package com.hexagonaldemo.paymentapi.common.rest;

public class Response<T> {

    private T data;
    private ErrorResponse errors;

    public Response() {
    }

    public Response(ErrorResponse errors) {
        this.errors = errors;
    }

    public Response(T data) {
        this.data = data;
    }

    public ErrorResponse getErrors() {
        return errors;
    }

    public T getData() {
        return data;
    }
}