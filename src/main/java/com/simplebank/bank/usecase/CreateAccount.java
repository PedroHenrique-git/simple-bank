package com.simplebank.bank.usecase;

import com.simplebank.bank.data.gateway.AccountRepositoryGateway;
import com.simplebank.bank.domain.model.Account.Account;

public class CreateAccount implements UseCase<Account, Account>
{
  public AccountRepositoryGateway repository;

  public CreateAccount(AccountRepositoryGateway repository)
  {
    this.repository = repository;
  }

  @Override
  public Account execute(Account account)
  {
    return repository.save(account);
  }
}
