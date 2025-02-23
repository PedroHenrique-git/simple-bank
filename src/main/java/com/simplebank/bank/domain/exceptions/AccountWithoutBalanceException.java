package com.simplebank.bank.domain.exceptions;

public class AccountWithoutBalanceException extends Exception
{
  public AccountWithoutBalanceException(String message)
  {
    super(message);
  }
}
