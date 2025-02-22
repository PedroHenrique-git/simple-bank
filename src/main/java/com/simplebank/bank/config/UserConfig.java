package com.simplebank.bank.config;

import com.simplebank.bank.data.gateway.UserRepositoryGateway;
import com.simplebank.bank.infra.jpa.gateway.UserRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.mapper.UserEntityMapper;
import com.simplebank.bank.infra.jpa.repository.UserRepository;
import com.simplebank.bank.infra.validator.JakartaUserValidation;
import com.simplebank.bank.presentation.controller.ControllerOperation;
import com.simplebank.bank.presentation.controller.CreateUserOperation;
import com.simplebank.bank.presentation.controller.WebController;
import com.simplebank.bank.usecase.CreateUser;
import com.simplebank.bank.usecase.UseCase;
import com.simplebank.bank.usecase.mapper.UserDTOMapper;
import com.simplebank.bank.usecase.ports.CreateUserInputValidator;
import com.simplebank.bank.usecase.ports.UserDTORequest;
import com.simplebank.bank.usecase.ports.UserDTOResponse;
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
  public UserEntityMapper userEntityMapper()
  {
    return new UserEntityMapper();
  }

  @Bean
  public UseCase<UserDTORequest, UserDTOResponse> createUser(UserRepositoryGateway userRepository,
                                                             CreateUserInputValidator validator,
                                                             UserDTOMapper mapper)
  {
    return new CreateUser(userRepository, validator, mapper);
  }

  @Bean
  public CreateUserInputValidator createUserInputValidator()
  {
    return new JakartaUserValidation();
  }

  @Bean
  public UserDTOMapper userDTOMapper()
  {
    return new UserDTOMapper();
  }

  @Bean
  public ControllerOperation<UserDTOResponse, UserDTORequest> createUserOperation(
      UseCase<UserDTORequest, UserDTOResponse> useCase)
  {
    return new CreateUserOperation(useCase);
  }

  @Bean
  public WebController<UserDTOResponse, UserDTORequest> createUserWebController(
      ControllerOperation<UserDTOResponse, UserDTORequest> operation)
  {
    return new WebController<>(operation);
  }
}
