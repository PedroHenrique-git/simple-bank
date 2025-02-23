package com.simplebank.bank.infra.jpa.factories;

import com.simplebank.bank.infra.jpa.entities.ClientUserEntity;
import com.simplebank.bank.infra.jpa.entities.UserEntity;

public class ClientUserEntityFactory extends AbstractUserEntityFactory
{
  @Override
  public UserEntity make()
  {
    return new ClientUserEntity();
  }
}
