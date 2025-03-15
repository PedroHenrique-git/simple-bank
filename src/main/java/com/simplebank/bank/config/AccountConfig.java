package com.simplebank.bank.config;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.data.gateways.TransactionRepositoryGateway;
import com.simplebank.bank.domain.factories.AccountFactoryMaker;
import com.simplebank.bank.domain.factories.UserFactoryMaker;
import com.simplebank.bank.infra.jpa.factories.AccountEntityFactoryMaker;
import com.simplebank.bank.infra.jpa.gateways.AccountRepositoryJpaGateway;
import com.simplebank.bank.infra.jpa.mappers.AccountEntityMapper;
import com.simplebank.bank.infra.jpa.mappers.TransactionEntityMapper;
import com.simplebank.bank.infra.jpa.mappers.UserEntityMapper;
import com.simplebank.bank.infra.jpa.repositories.AccountRepository;
import com.simplebank.bank.infra.notification.NotificationSender;
import com.simplebank.bank.infra.validators.JakartaAccountValidation;
import com.simplebank.bank.infra.validators.JakartaDepositValidation;
import com.simplebank.bank.presentation.controllers.ControllerOperation;
import com.simplebank.bank.presentation.controllers.CreateAccountOperation;
import com.simplebank.bank.presentation.controllers.DepositOperation;
import com.simplebank.bank.presentation.controllers.TransferOperation;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.usecases.CreateAccount;
import com.simplebank.bank.usecases.Deposit;
import com.simplebank.bank.usecases.Transfer;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.mapper.CreateAccountDTOMapper;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import com.simplebank.bank.usecases.ports.Encoder;
import com.simplebank.bank.usecases.ports.InputValidator;
import com.simplebank.bank.usecases.ports.TransferAuthService;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;
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
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccount(

      AccountRepositoryGateway accountRepository,
      InputValidator<CreateAccountDTORequest> validator,
      CreateAccountDTOMapper mapper, Encoder encoder)
  {
    return new CreateAccount(accountRepository, validator,
        mapper,
        encoder);
  }

  @Bean
  public InputValidator<CreateAccountDTORequest> createAccountInputValidator()
  {
    return new JakartaAccountValidation();
  }

  @Bean
  public CreateAccountDTOMapper accountDTOMapper(AccountFactoryMaker factory,
                                                 UserFactoryMaker userFactoryMaker)
  {
    return new CreateAccountDTOMapper(factory, userFactoryMaker);
  }

  @Bean
  public ControllerOperation<CreateAccountDTOResponse, CreateAccountDTORequest> createAccountOperation(
      UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> useCase)
  {
    return new CreateAccountOperation(useCase);
  }

  @Bean
  public ControllerOperation<DepositDTOResponse, DepositDTORequest> createDepositOperation(
      UseCase<DepositDTORequest, DepositDTOResponse> useCase)
  {
    return new DepositOperation(useCase);
  }

  @Bean
  public ControllerOperation<TransferDTOResponse, TransferDTORequest> transferOperation(
      UseCase<TransferDTORequest, TransferDTOResponse> useCase)
  {
    return new TransferOperation(useCase);
  }

  @Bean
  public WebController<TransferDTOResponse, TransferDTORequest> transferWebController(
      ControllerOperation<TransferDTOResponse, TransferDTORequest> operation)
  {
    return new WebController<>(operation);
  }

  @Bean
  public WebController<CreateAccountDTOResponse, CreateAccountDTORequest> createAccountWebController(
      ControllerOperation<CreateAccountDTOResponse, CreateAccountDTORequest> operation)
  {
    return new WebController<>(operation);
  }

  @Bean
  public WebController<DepositDTOResponse, DepositDTORequest> depositWebController(
      ControllerOperation<DepositDTOResponse, DepositDTORequest> operation)
  {
    return new WebController<>(operation);
  }

  @Bean
  public UseCase<DepositDTORequest, DepositDTOResponse> deposit(
      AccountRepositoryGateway accountRepository, InputValidator<DepositDTORequest> validator)
  {
    return new Deposit(accountRepository, validator);
  }

  @Bean
  public UseCase<TransferDTORequest, TransferDTOResponse> transfer(
      AccountRepositoryGateway accountRepository,
      TransactionRepositoryGateway transactionRepository, TransferAuthService transferAuthService,
      NotificationSender notificationSender)
  {
    return new Transfer(accountRepository, transactionRepository, transferAuthService,
        notificationSender);
  }

  @Bean
  public InputValidator<DepositDTORequest> depositInputValidator()
  {
    return new JakartaDepositValidation();
  }
}
