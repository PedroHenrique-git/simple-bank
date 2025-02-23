package com.simplebank.bank.data.gateways;

import com.simplebank.bank.domain.models.Account.Account;

public interface AccountRepositoryGateway
{
  Account save(Account account);

  Account deposit(Account account);

  Account find(long id);

  void delete(long id);
}
