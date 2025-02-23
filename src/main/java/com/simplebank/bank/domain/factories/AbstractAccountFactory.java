package com.simplebank.bank.domain.factories;

import com.simplebank.bank.domain.models.Account.Account;

public abstract class AbstractAccountFactory
{
  public abstract Account make();
}
