package com.simplebank.bank.presentation.controller;

import com.simplebank.bank.domain.exception.ValidationErrorException;
import com.simplebank.bank.presentation.controller.port.HttpRequest;
import com.simplebank.bank.presentation.controller.port.HttpResponse;

public interface ControllerOperation<T, R>
{
  HttpResponse<T> execute(HttpRequest<R> request) throws ValidationErrorException;
}
