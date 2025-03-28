package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.InvalidAmountException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import com.simplebank.bank.usecases.ports.InputValidator;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class Deposit implements UseCase<DepositDTORequest, DepositDTOResponse>
{
  private final AccountRepositoryGateway repository;
  private final InputValidator<DepositDTORequest> validator;
  private final AuthManager authManager;

  public Deposit(AccountRepositoryGateway repository, InputValidator<DepositDTORequest> validator,
                 AuthManager authManager)
  {
    this.repository = repository;
    this.validator = validator;
    this.authManager = authManager;
  }

  @Override
  @Transactional
  public DepositDTOResponse execute(DepositDTORequest dto)
      throws UseCaseException, ForbiddenException
  {
    try
    {
      var violations = validator.validate(dto);

      if (!violations.isEmpty())
      {
        throw new UseCaseException("Invalid deposit input", violations);
      }

      var account = repository.find(dto.accountId());

      if (account == null)
      {
        throw new UseCaseException("Invalid deposit input", List.of("Invalid accountId"));
      }

      authManager.isAuthorized(account.getUser().getId());

      account.deposit(dto.amount());

      var updatedAccount = repository.update(account);

      return new DepositDTOResponse(updatedAccount.getBalance());
    } catch (InvalidAmountException e)
    {
      throw new UseCaseException("Invalid deposit input", List.of(e.getMessage()));
    }
  }
}
