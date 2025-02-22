package com.simplebank.bank.presentation.controller;

import com.simplebank.bank.domain.exception.ValidationErrorException;
import com.simplebank.bank.presentation.controller.port.HttpRequest;
import com.simplebank.bank.presentation.controller.port.HttpResponse;

public class WebController<T, R> {
    private ControllerOperation<T, R> operation;

    public WebController(ControllerOperation<T, R> operation) {
        this.operation = operation;
    }

    public HttpResponse<T> handle(HttpRequest<R> request) {
        try {
            return operation.execute(request);
        } catch (ValidationErrorException e) {
            return new HttpResponse<>(400, false, e.getMessage(), (T) e.getErrors());
        } catch (Exception e) {
            return new HttpResponse<>(500, false, "Something went wrong, try again later", null);
        }
    }
}
