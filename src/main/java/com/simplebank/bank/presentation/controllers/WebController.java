package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ValidationErrorException;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;

public class WebController<T, R>
{
  private final ControllerOperation<T, R> operation;

  public WebController(ControllerOperation<T, R> operation)
  {
    this.operation = operation;
  }

  public HttpResponse<T> handle(HttpRequest<R> request)
  {
    try
    {
      return operation.execute(request);
    } catch (ValidationErrorException e)
    {
      return new HttpResponse<>(400, false, e.getMessage(), (T) e.getErrors());
    } catch (Exception e)
    {
      return new HttpResponse<>(500, false, "Something went wrong, try again later", null);
    }
  }
}
