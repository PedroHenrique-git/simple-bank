package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.TransactionRepositoryGateway;
import com.simplebank.bank.infra.jpa.mapper.AccountEntityMapper;
import com.simplebank.bank.infra.jpa.mapper.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.gateway.TransactionRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {
    @Bean
    public TransactionRepositoryGateway transactionRepositoryGateway(TransactionRepository repository, TransactionEntityMapper mapper, AccountEntityMapper accountMapper) {
        return new TransactionRepositoryJpaGateway(repository, mapper, accountMapper);
    }

    @Bean
    public TransactionEntityMapper transactionEntityMapper() {
        return new TransactionEntityMapper();
    }
}
