package com.simplebank.bank.infra.jpa.factories;

import com.simplebank.bank.infra.jpa.entities.BusinessUserEntity;
import com.simplebank.bank.infra.jpa.entities.UserEntity;

public class BusinessUserEntityFactory extends AbstractUserEntityFactory
{
  @Override
  public UserEntity make()
  {
    return new BusinessUserEntity();
  }
}
