package com.simplebank.bank.config;

import com.simplebank.bank.infra.auth.TransferAuthServiceInRest;
import com.simplebank.bank.infra.encoders.BcryptEncoder;
import com.simplebank.bank.usecases.ports.Encoder;
import com.simplebank.bank.usecases.ports.TransferAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CommonConfig
{
  @Bean
  public TransferAuthService transferAuthService(
      @Value("${transfer.auth.url}") String transferAuthUrl)
  {
    return new TransferAuthServiceInRest(transferAuthUrl);
  }

  @Bean
  public Encoder encoder(BCryptPasswordEncoder bCryptPasswordEncoder)
  {
    return new BcryptEncoder(bCryptPasswordEncoder);
  }
}
