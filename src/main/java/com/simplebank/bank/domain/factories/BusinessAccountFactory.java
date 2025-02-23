package com.simplebank.bank.domain.factories;

import com.simplebank.bank.domain.models.Account.Account;
import com.simplebank.bank.domain.models.Account.BusinessAccount;

public class BusinessAccountFactory extends AbstractAccountFactory
{
  @Override
  public Account make()
  {
    return new BusinessAccount();
  }
}
