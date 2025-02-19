package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.CommonUserRepositoryGateway;
import com.simplebank.bank.infra.jpa.gateway.CommonAccountRepositoryJpaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonUserConfig {
    @Bean
    public CommonUserRepositoryGateway commonUserGateway() {
        return new CommonAccountRepositoryJpaGateway();
    }
}
