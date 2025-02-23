package com.simplebank.bank.infra.jpa.factories;

import com.simplebank.bank.infra.jpa.entities.AccountEntity;

public abstract class AbstractAccountEntityFactory
{
  public abstract AccountEntity make();
}
