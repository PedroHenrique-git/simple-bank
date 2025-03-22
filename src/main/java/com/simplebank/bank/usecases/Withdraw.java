package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.InputValidator;
import com.simplebank.bank.usecases.ports.WithdrawDTORequest;
import com.simplebank.bank.usecases.ports.WithdrawDTOResponse;
import org.springframework.transaction.annotation.Transactional;

public class Withdraw implements UseCase<WithdrawDTORequest, WithdrawDTOResponse>
{
  private final AccountRepositoryGateway repository;
  private final InputValidator<WithdrawDTORequest> validator;
  private final AuthManager authManager;

  public Withdraw(AccountRepositoryGateway repository, InputValidator<WithdrawDTORequest> validator,
                  AuthManager authManager)
  {
    this.repository = repository;
    this.validator = validator;
    this.authManager = authManager;
  }

  @Override
  @Transactional
  public WithdrawDTOResponse execute(WithdrawDTORequest dto)
      throws UseCaseException, ForbiddenException
  {
    try
    {
      var violations = validator.validate(dto);

      if (!violations.isEmpty())
      {
        throw new UseCaseException("Invalid withdraw input", violations);
      }

      var account = repository.find(dto.accountId());

      if (account == null)
      {
        throw new UseCaseException("Invalid accountId");
      }

      authManager.isAuthorized(account.getUser().getId());

      account.withdraw(dto.amount());

      var updatedAccount = repository.update(account);

      return new WithdrawDTOResponse(updatedAccount.getBalance());
    } catch (AccountWithoutBalanceException e)
    {
      throw new UseCaseException(e.getMessage());
    }
  }
}
