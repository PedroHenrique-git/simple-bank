package com.simplebank.bank.config;

import com.simplebank.bank.data.gateways.UserRepositoryGateway;
import com.simplebank.bank.domain.factories.UserFactoryMaker;
import com.simplebank.bank.infra.jpa.factories.UserEntityFactoryMaker;
import com.simplebank.bank.infra.jpa.gateways.UserRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.mappers.UserEntityMapper;
import com.simplebank.bank.infra.jpa.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig
{
  @Bean
  public UserRepositoryGateway userRepositoryGateway(UserRepository repository,
                                                     UserEntityMapper mapper)
  {
    return new UserRepositoryJpaGateway(repository, mapper);
  }

  @Bean
  public UserEntityMapper userEntityMapper(UserFactoryMaker userFactory,
                                           UserEntityFactoryMaker userEntityFactoryMaker)
  {
    return new UserEntityMapper(userFactory, userEntityFactoryMaker);
  }

  @Bean
  public UserFactoryMaker userFactoryMaker()
  {
    return new UserFactoryMaker();
  }

  @Bean
  public UserEntityFactoryMaker userEntityFactoryMaker()
  {
    return new UserEntityFactoryMaker();
  }
}
