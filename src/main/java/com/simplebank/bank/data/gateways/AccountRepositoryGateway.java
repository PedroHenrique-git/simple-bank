package com.simplebank.bank.data.gateways;

import com.simplebank.bank.domain.models.Account.Account;

public interface AccountRepositoryGateway
{
  Account save(Account account);

  Account update(Account account);

  Account find(long id);

  Account findByUserId(long id);

  void delete(long id);
}
