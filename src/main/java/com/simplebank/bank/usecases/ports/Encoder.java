package com.simplebank.bank.usecases.ports;

public interface Encoder
{
  String encode(String raw);

  boolean compare(String raw, String encoded);
}
