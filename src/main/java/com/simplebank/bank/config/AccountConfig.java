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
import com.simplebank.bank.infra.validators.JakartaAccountValidation;
import com.simplebank.bank.infra.validators.JakartaDepositValidation;
import com.simplebank.bank.infra.validators.JakartaTransferValidation;
import com.simplebank.bank.infra.validators.JakartaWithdrawValidation;
import com.simplebank.bank.presentation.controllers.AuthenticatedUserAccountOperation;
import com.simplebank.bank.presentation.controllers.ControllerOperation;
import com.simplebank.bank.presentation.controllers.CreateAccountOperation;
import com.simplebank.bank.presentation.controllers.DepositOperation;
import com.simplebank.bank.presentation.controllers.TransferOperation;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.WithdrawOperation;
import com.simplebank.bank.services.TransferAuthService;
import com.simplebank.bank.usecases.AuthenticatedUserAccount;
import com.simplebank.bank.usecases.CreateAccount;
import com.simplebank.bank.usecases.Deposit;
import com.simplebank.bank.usecases.Transfer;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.Withdraw;
import com.simplebank.bank.usecases.mapper.CreateAccountDTOMapper;
import com.simplebank.bank.usecases.mapper.TransactionDTOMapper;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTORequest;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTOResponse;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import com.simplebank.bank.usecases.ports.Encoder;
import com.simplebank.bank.usecases.ports.InputValidator;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;
import com.simplebank.bank.usecases.ports.TransferNotificationSender;
import com.simplebank.bank.usecases.ports.WithdrawDTORequest;
import com.simplebank.bank.usecases.ports.WithdrawDTOResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

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
                                                 AccountEntityFactoryMaker accountEntityFactoryMaker,
                                                 @Lazy TransactionEntityMapper transactionMapper)
  {
    return new AccountEntityMapper(userMapper, accountFactoryMaker, accountEntityFactoryMaker,
        transactionMapper);
  }

  @Bean
  public TransactionDTOMapper transactionDTOMapper()
  {
    return new TransactionDTOMapper();
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
      UseCase<TransferDTORequest, TransferDTOResponse> usecase)
  {
    return new TransferOperation(usecase);
  }

  @Bean
  public ControllerOperation<WithdrawDTOResponse, WithdrawDTORequest> withdrawOperation(
      UseCase<WithdrawDTORequest, WithdrawDTOResponse> usecase)
  {
    return new WithdrawOperation(usecase);
  }

  @Bean
  public WebController<WithdrawDTOResponse, WithdrawDTORequest> withdrawWebController(
      ControllerOperation<WithdrawDTOResponse, WithdrawDTORequest> operation)
  {
    return new WebController<>(operation);
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
      AccountRepositoryGateway accountRepository, InputValidator<DepositDTORequest> validator,
      AuthManager authManager)
  {
    return new Deposit(accountRepository, validator, authManager);
  }

  @Bean
  public UseCase<TransferDTORequest, TransferDTOResponse> transfer(
      AccountRepositoryGateway accountRepository,
      TransactionRepositoryGateway transactionRepository, TransferAuthService transferAuthService,
      TransferNotificationSender notificationSender, InputValidator<TransferDTORequest> validator,
      AuthManager authManager)
  {
    return new Transfer(accountRepository, transactionRepository, transferAuthService,
        notificationSender, validator, authManager);
  }

  @Bean
  public UseCase<WithdrawDTORequest, WithdrawDTOResponse> withdraw(
      AccountRepositoryGateway accountRepository, InputValidator<WithdrawDTORequest> validator,
      AuthManager authManager)
  {
    return new Withdraw(accountRepository, validator, authManager);
  }

  @Bean
  public UseCase<AuthenticatedUserAccountDTORequest, AuthenticatedUserAccountDTOResponse> authenticatedUserAccount(
      AuthManager authManager, AccountRepositoryGateway accountRepository,
      TransactionDTOMapper dtoMapper)
  {
    return new AuthenticatedUserAccount(authManager, accountRepository, dtoMapper);
  }

  @Bean
  public ControllerOperation<AuthenticatedUserAccountDTOResponse, AuthenticatedUserAccountDTORequest> authenticatedUserAccountOperation(
      UseCase<AuthenticatedUserAccountDTORequest, AuthenticatedUserAccountDTOResponse> usecase)
  {
    return new AuthenticatedUserAccountOperation(usecase);
  }

  @Bean
  public WebController<AuthenticatedUserAccountDTOResponse, AuthenticatedUserAccountDTORequest> authenticatedUserAccountWebController(
      ControllerOperation<AuthenticatedUserAccountDTOResponse, AuthenticatedUserAccountDTORequest> operation)
  {
    return new WebController<>(operation);
  }

  @Bean
  public InputValidator<DepositDTORequest> depositInputValidator()
  {
    return new JakartaDepositValidation();
  }

  @Bean
  public InputValidator<TransferDTORequest> transferInputValidator()
  {
    return new JakartaTransferValidation();
  }

  @Bean
  public InputValidator<WithdrawDTORequest> withdrawInputValidator()
  {
    return new JakartaWithdrawValidation();
  }
}
