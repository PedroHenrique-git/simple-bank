package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.UnitOfWorkGateway;
import com.simplebank.bank.infra.RegexMap;
import com.simplebank.bank.infra.jpa.gateway.UnitOfWorkJpaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    @Bean
    public UnitOfWorkGateway unitOfWork() {
        return new UnitOfWorkJpaGateway();
    }
}
