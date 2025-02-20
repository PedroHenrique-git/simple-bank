package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.CommonAccountRepositoryGateway;
import com.simplebank.bank.infra.jpa.adapter.CommonAccountJpaEntityMapper;
import com.simplebank.bank.infra.jpa.adapter.CommonUserJpaEntityMapper;
import com.simplebank.bank.infra.jpa.adapter.TransactionJpaEntityMapper;
import com.simplebank.bank.infra.jpa.gateway.CommonAccountRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.repository.CommonAccountJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonAccountConfig {
    @Bean
    public CommonAccountRepositoryGateway commonAccountRepositoryGateway(CommonAccountJpaRepository repository, CommonAccountJpaEntityMapper mapper, TransactionJpaEntityMapper transactionMapper) {
        return new CommonAccountRepositoryJpaGateway(repository, mapper, transactionMapper);
    }

    @Bean
    public CommonAccountJpaEntityMapper commonAccountJpaEntityMapper(CommonUserJpaEntityMapper userMapper) {
        return new CommonAccountJpaEntityMapper(userMapper);
    }
}
