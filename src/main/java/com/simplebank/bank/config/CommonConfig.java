package com.simplebank.bank.config;

import com.simplebank.bank.infra.encoders.BcryptEncoder;
import com.simplebank.bank.usecases.ports.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CommonConfig
{
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder()
  {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Encoder encoder(BCryptPasswordEncoder bCryptPasswordEncoder)
  {
    return new BcryptEncoder(bCryptPasswordEncoder);
  }
}
