package com.simplebank.bank.infra.jpa.factories;

import com.simplebank.bank.infra.jpa.entities.UserEntity;

public abstract class AbstractUserEntityFactory
{
  public abstract UserEntity make();
}
