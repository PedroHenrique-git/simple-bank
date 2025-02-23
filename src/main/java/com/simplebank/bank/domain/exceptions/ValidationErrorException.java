package com.simplebank.bank.domain.exceptions;

import java.util.List;

public class ValidationErrorException extends Exception
{
  private final List<String> errors;

  public ValidationErrorException(String message, List<String> errors)
  {
    super(message);

    this.errors = errors;
  }

  public List<String> getErrors()
  {
    return errors;
  }
}
