package com.simplebank.bank.domain.factories;

import com.simplebank.bank.domain.models.User.User;

public abstract class AbstractUserFactory
{
  public abstract User make();
}
