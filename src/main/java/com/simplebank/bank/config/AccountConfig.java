package com.simplebank.bank.config;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.factories.AccountFactoryMaker;
import com.simplebank.bank.domain.factories.UserFactoryMaker;
import com.simplebank.bank.infra.jpa.factories.AccountEntityFactoryMaker;
import com.simplebank.bank.infra.jpa.gateways.AccountRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.mappers.AccountEntityMapper;
import com.simplebank.bank.infra.jpa.mappers.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.mappers.UserEntityMapper;
import com.simplebank.bank.infra.jpa.repositories.AccountRepository;
import com.simplebank.bank.infra.validators.JakartaAccountValidation;
import com.simplebank.bank.presentation.controllers.ControllerOperation;
import com.simplebank.bank.presentation.controllers.CreateAccountOperation;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.usecases.CreateAccount;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.mapper.AccountDTOMapper;
import com.simplebank.bank.usecases.ports.AccountDTORequest;
import com.simplebank.bank.usecases.ports.AccountDTOResponse;
import com.simplebank.bank.usecases.ports.CreateAccountInputValidator;
import com.simplebank.bank.usecases.ports.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig
{
  @Bean
  public AccountRepositoryGateway accountRepositoryGateway(AccountRepository repository,
                                                           AccountEntityMapper mapper,
                                                           TransactionEntityMapper transactionMapper)
  {
    return new AccountRepositoryJpaGateway(repository, mapper, transactionMapper);
  }

  @Bean
  public AccountEntityMapper accountEntityMapper(UserEntityMapper userMapper,
                                                 AccountFactoryMaker accountFactoryMaker,
                                                 AccountEntityFactoryMaker accountEntityFactoryMaker)
  {
    return new AccountEntityMapper(userMapper, accountFactoryMaker, accountEntityFactoryMaker);
  }

  @Bean
  public AccountFactoryMaker accountFactoryMaker()
  {
    return new AccountFactoryMaker();
  }

  @Bean
  public AccountEntityFactoryMaker accountEntityFactoryMaker()
  {
    return new AccountEntityFactoryMaker();
  }

  @Bean
  public UseCase<AccountDTORequest, AccountDTOResponse> createAccount(

      AccountRepositoryGateway accountRepository,
      CreateAccountInputValidator validator,
      AccountDTOMapper mapper, Encoder encoder)
  {
    return new CreateAccount(accountRepository, validator,
        mapper,
        encoder);
  }

  @Bean
  public CreateAccountInputValidator createAccountInputValidator()
  {
    return new JakartaAccountValidation();
  }

  @Bean
  public AccountDTOMapper accountDTOMapper(AccountFactoryMaker factory,
                                           UserFactoryMaker userFactoryMaker)
  {
    return new AccountDTOMapper(factory, userFactoryMaker);
  }

  @Bean
  public ControllerOperation<AccountDTOResponse, AccountDTORequest> createAccountOperation(
      UseCase<AccountDTORequest, AccountDTOResponse> useCase)
  {
    return new CreateAccountOperation(useCase);
  }

  @Bean
  public WebController<AccountDTOResponse, AccountDTORequest> createAccountWebController(
      ControllerOperation<AccountDTOResponse, AccountDTORequest> operation)
  {
    return new WebController<>(operation);
  }
}
