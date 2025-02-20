package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.AccountRepositoryGateway;
import com.simplebank.bank.infra.jpa.mapper.AccountEntityMapper;
import com.simplebank.bank.infra.jpa.mapper.UserEntityMapper;
import com.simplebank.bank.infra.jpa.mapper.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.gateway.AccountRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.repository.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {
    @Bean
    public AccountRepositoryGateway commonAccountRepositoryGateway(AccountRepository repository, AccountEntityMapper mapper, TransactionEntityMapper transactionMapper) {
        return new AccountRepositoryJpaGateway(repository, mapper, transactionMapper);
    }

    @Bean
    public AccountEntityMapper commonAccountJpaEntityMapper(UserEntityMapper userMapper) {
        return new AccountEntityMapper(userMapper);
    }
}
