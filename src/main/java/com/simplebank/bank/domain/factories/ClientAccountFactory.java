package com.simplebank.bank.domain.factories;

import com.simplebank.bank.domain.models.Account.Account;
import com.simplebank.bank.domain.models.Account.ClientAccount;

public class ClientAccountFactory extends AbstractAccountFactory
{

  @Override
  public Account make()
  {
    return new ClientAccount();
  }
}
