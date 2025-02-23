package com.simplebank.bank.config;

import com.simplebank.bank.infra.encoders.BcryptEncoder;
import com.simplebank.bank.usecases.ports.Encoder;
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

  @Bean
  public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager)
  {
    return new TransactionTemplate(transactionManager);
  }
}
