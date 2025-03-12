package com.simplebank.bank.presentation.controllers.ports;

import java.util.List;

public record HttpResponse<T>(int status, boolean success, String message, T body,
                              List<String> errors)
{
  public HttpResponse(int status, boolean success, String message, T body)
  {
    this(status, success, message, body, List.of());
  }
}
