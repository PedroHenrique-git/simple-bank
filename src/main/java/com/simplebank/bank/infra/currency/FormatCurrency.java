package com.simplebank.bank.infra.currency;


import java.text.NumberFormat;

public class FormatCurrency
{
  public static String format(double value)
  {
    NumberFormat formatter = NumberFormat.getCurrencyInstance();

    return formatter.format(value);
  }
}
