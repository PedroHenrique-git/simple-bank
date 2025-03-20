package com.simplebank.bank.security.services;

public enum TokenType
{
  COMMON("COMMON"),
  REFRESH("REFRESH");

  private final String value;

  TokenType(String value)
  {
    this.value = value;
  }

  public String value()
  {
    return this.value;
  }
}
