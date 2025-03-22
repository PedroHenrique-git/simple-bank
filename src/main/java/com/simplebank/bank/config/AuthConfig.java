package com.simplebank.bank.config;

import com.simplebank.bank.presentation.controllers.AuthenticatedUserOperation;
import com.simplebank.bank.presentation.controllers.ControllerOperation;
import com.simplebank.bank.presentation.controllers.LoginOperation;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.usecases.AuthenticatedUser;
import com.simplebank.bank.usecases.Login;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTORequest;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTOResponse;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig
{
  @Bean
  public UseCase<AuthLoginDTORequest, AuthLoginDTOResponse> login(AuthManager manager)
  {
    return new Login(manager);
  }

  @Bean
  public UseCase<AuthAuthenticatedUserDTORequest, AuthAuthenticatedUserDTOResponse> authenticatedUser(
      AuthManager manager)
  {
    return new AuthenticatedUser(manager);
  }

  @Bean
  public ControllerOperation<AuthLoginDTOResponse, AuthLoginDTORequest> loginOperation(
      UseCase<AuthLoginDTORequest, AuthLoginDTOResponse> usecase)
  {
    return new LoginOperation(usecase);
  }

  @Bean
  public ControllerOperation<AuthAuthenticatedUserDTOResponse, AuthAuthenticatedUserDTORequest> authenticatedUserOperation(
      UseCase<AuthAuthenticatedUserDTORequest, AuthAuthenticatedUserDTOResponse> usecase)
  {
    return new AuthenticatedUserOperation(usecase);
  }

  @Bean
  public WebController<AuthLoginDTOResponse, AuthLoginDTORequest> loginWebController(
      ControllerOperation<AuthLoginDTOResponse, AuthLoginDTORequest> operation)
  {
    return new WebController<>(operation);
  }

  @Bean
  public WebController<AuthAuthenticatedUserDTOResponse, AuthAuthenticatedUserDTORequest> authenticatedUserWebController(
      ControllerOperation<AuthAuthenticatedUserDTOResponse, AuthAuthenticatedUserDTORequest> operation)
  {
    return new WebController<>(operation);
  }
}
