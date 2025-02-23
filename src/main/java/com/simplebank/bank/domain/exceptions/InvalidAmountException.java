package com.simplebank.bank.domain.exceptions;

public class InvalidAmountException extends Exception
{
  public InvalidAmountException(String message)
  {
    super(message);
  }
}
