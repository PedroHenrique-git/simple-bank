package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.CommonUserRepositoryGateway;
import com.simplebank.bank.infra.jpa.adapter.CommonUserJpaEntityMapper;
import com.simplebank.bank.infra.jpa.gateway.CommonUserRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.repository.CommonUserRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonUserConfig {
    @Bean
    public CommonUserRepositoryGateway commonUserRepositoryGateway(CommonUserRepositoryJpa repository, CommonUserJpaEntityMapper mapper) {
        return new CommonUserRepositoryJpaGateway(repository, mapper);
    }

    @Bean
    public CommonUserJpaEntityMapper commonUserJpaEntityMapper() {
        return new CommonUserJpaEntityMapper();
    }
}
