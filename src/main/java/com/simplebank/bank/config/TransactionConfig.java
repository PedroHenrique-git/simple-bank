package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.TransactionRepositoryGateway;
import com.simplebank.bank.infra.jpa.adapter.CommonAccountJpaEntityMapper;
import com.simplebank.bank.infra.jpa.adapter.TransactionJpaEntityMapper;
import com.simplebank.bank.infra.jpa.gateway.TransactionRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.repository.TransactionRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {
    @Bean
    public TransactionRepositoryGateway transactionRepositoryGateway(TransactionRepositoryJpa repository, TransactionJpaEntityMapper mapper, CommonAccountJpaEntityMapper accountMapper) {
        return new TransactionRepositoryJpaGateway(repository, mapper, accountMapper);
    }

    @Bean
    public TransactionJpaEntityMapper transactionJpaEntityMapper() {
        return new TransactionJpaEntityMapper();
    }
}
