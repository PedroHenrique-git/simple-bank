package com.simplebank.bank.domain.exceptions;

import java.util.List;

public class UseCaseException extends Exception
{
  private final List<String> errors;

  public UseCaseException(String message, List<String> errors)
  {
    super(message);

    this.errors = errors;
  }

  public UseCaseException(String message)
  {
    super(message);

    this.errors = List.of();
  }

  public List<String> getErrors()
  {
    return errors;
  }
}
