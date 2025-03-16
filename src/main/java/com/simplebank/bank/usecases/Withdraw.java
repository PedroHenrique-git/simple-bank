package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.InputValidator;
import com.simplebank.bank.usecases.ports.WithdrawDTORequest;
import com.simplebank.bank.usecases.ports.WithdrawDTOResponse;

public class Withdraw implements UseCase<WithdrawDTORequest, WithdrawDTOResponse>
{
  private final AccountRepositoryGateway repository;
  private final InputValidator<WithdrawDTORequest> validator;

  public Withdraw(AccountRepositoryGateway repository, InputValidator<WithdrawDTORequest> validator)
  {
    this.repository = repository;
    this.validator = validator;
  }

  @Override
  public WithdrawDTOResponse execute(WithdrawDTORequest dto) throws UseCaseException
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

      account.withdraw(dto.amount());

      var updatedAccount = repository.update(account);

      return new WithdrawDTOResponse(updatedAccount.getBalance());
    } catch (AccountWithoutBalanceException e)
    {
      throw new UseCaseException(e.getMessage());
    }
  }
}
