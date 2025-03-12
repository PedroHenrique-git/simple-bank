package com.simplebank.bank.presentation.controllers.http;

public enum HttpStatus
{
  CREATED(201),
  SUCCESS(200),
  INTERNAL_SERVER_ERROR(500),
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
  FORBIDDEN(403);

  private final int value;

  HttpStatus(int value)
  {
    this.value = value;
  }

  public int value()
  {
    return this.value;
  }
}
