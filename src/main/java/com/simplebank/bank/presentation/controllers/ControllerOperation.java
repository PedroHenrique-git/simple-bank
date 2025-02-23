package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ValidationErrorException;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;

public interface ControllerOperation<T, R>
{
  HttpResponse<T> execute(HttpRequest<R> request) throws ValidationErrorException;
}
