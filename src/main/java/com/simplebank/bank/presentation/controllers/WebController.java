package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
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
    } catch (UseCaseException e)
    {
      return new HttpResponse<>(HttpStatus.BAD_REQUEST.value(), false, e.getMessage(), null,
          e.getErrors());
    } catch (Exception e)
    {
      return new HttpResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false,
          "Something went wrong, try again later", null);
    }
  }
}
