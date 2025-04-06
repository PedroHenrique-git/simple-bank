package com.simplebank.bank.domain.exceptions;

public class InvalidToken extends Exception
{
  public InvalidToken(String message)
  {
    super(message);
  }
}
