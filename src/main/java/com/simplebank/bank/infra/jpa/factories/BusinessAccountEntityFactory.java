package com.simplebank.bank.infra.jpa.factories;

import com.simplebank.bank.infra.jpa.entities.AccountEntity;
import com.simplebank.bank.infra.jpa.entities.BusinessAccountEntity;

public class BusinessAccountEntityFactory extends AbstractAccountEntityFactory
{
  @Override
  public AccountEntity make()
  {
    return new BusinessAccountEntity();
  }
}
