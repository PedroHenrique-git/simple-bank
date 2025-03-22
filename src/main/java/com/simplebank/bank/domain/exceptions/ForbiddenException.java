package com.simplebank.bank.domain.exceptions;

public class ForbiddenException extends Exception
{
  public ForbiddenException()
  {
    super("You can not perform this operation");
  }

  public ForbiddenException(String message)
  {
    super(message);
  }
}
