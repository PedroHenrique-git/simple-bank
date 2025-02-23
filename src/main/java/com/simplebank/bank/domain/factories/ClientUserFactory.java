package com.simplebank.bank.domain.factories;

import com.simplebank.bank.domain.models.User.ClientUser;
import com.simplebank.bank.domain.models.User.User;

public class ClientUserFactory extends AbstractUserFactory
{
  @Override
  public User make()
  {
    return new ClientUser();
  }
}
