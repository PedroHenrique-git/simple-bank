package com.simplebank.bank.config;

import com.simplebank.bank.data.gateways.TransactionRepositoryGateway;
import com.simplebank.bank.infra.jpa.gateways.TransactionRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.mappers.AccountEntityMapper;
import com.simplebank.bank.infra.jpa.mappers.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.repositories.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig
{
  @Bean
  public TransactionRepositoryGateway transactionRepositoryGateway(TransactionRepository repository,
                                                                   TransactionEntityMapper mapper)
  {
    return new TransactionRepositoryJpaGateway(repository, mapper);
  }

  @Bean
  public TransactionEntityMapper transactionEntityMapper(AccountEntityMapper accountMapper)
  {
    return new TransactionEntityMapper(accountMapper);
  }
}
