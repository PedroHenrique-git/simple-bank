package com.simplebank.bank.domain;

public class Constants
{
  public static final long AUTH_TOKEN_EXPIRATION = 60; // minutes;
  public static final long REFRESH_TOKEN_EXPIRATION = 24 * 60 * 7; // Days
  public static final long REFRESH_TOKEN_COOKIE_EXPIRATION = 7; // Days
  public static final String REFRESH_TOKEN_COOKIE_NAME = "session";
}
