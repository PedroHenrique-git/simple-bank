package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.UserRepositoryGateway;
import com.simplebank.bank.infra.jpa.mapper.UserEntityMapper;
import com.simplebank.bank.infra.jpa.gateway.UserRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public UserRepositoryGateway userRepositoryGateway(UserRepository repository, UserEntityMapper mapper) {
        return new UserRepositoryJpaGateway(repository, mapper);
    }

    @Bean
    public UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }
}
