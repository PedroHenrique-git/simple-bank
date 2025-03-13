package com.simplebank.bank.config;

import com.simplebank.bank.infra.auth.TransferAuthServiceInRest;
import com.simplebank.bank.infra.encoders.BcryptEncoder;
import com.simplebank.bank.usecases.ports.Encoder;
import com.simplebank.bank.usecases.ports.TransferAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
public class CommonConfig
{
  @Value("${transfer.auth.url}")
  private String url;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder()
  {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TransferAuthService transferAuthService()
  {
    return new TransferAuthServiceInRest(url);
  }

  @Bean
  public Encoder encoder(BCryptPasswordEncoder bCryptPasswordEncoder)
  {
    return new BcryptEncoder(bCryptPasswordEncoder);
  }

  @Bean
  public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager)
  {
    return new TransactionTemplate(transactionManager);
  }
}
