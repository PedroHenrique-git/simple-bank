package com.simplebank.bank.infra.encoders;

import com.simplebank.bank.usecases.ports.Encoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptEncoder implements Encoder
{
  private final BCryptPasswordEncoder encoder;

  public BcryptEncoder(BCryptPasswordEncoder encoder)
  {
    this.encoder = encoder;
  }

  @Override
  public String encode(String raw)
  {
    return encoder.encode(raw);
  }

  @Override
  public boolean compare(String raw, String encoded)
  {
    return encoder.matches(raw, encoded);
  }
}
