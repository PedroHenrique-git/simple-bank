package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    } catch (UnauthorizedException e)
    {
      return new HttpResponse<>(HttpStatus.UNAUTHORIZED.value(), false,
          e.getMessage(), null);
    } catch (ForbiddenException e)
    {
      return new HttpResponse<>(HttpStatus.FORBIDDEN.value(), false,
          e.getMessage(), null);
    } catch (UseCaseException e)
    {
      return new HttpResponse<>(HttpStatus.BAD_REQUEST.value(), false, e.getMessage(), null,
          e.getErrors());
    } catch (Exception e)
    {
      log.error("[WEB CONTROLLER ERROR]: {}", e.toString());

      return new HttpResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), false,
          "Something went wrong, try again later", null);
    }
  }
}
