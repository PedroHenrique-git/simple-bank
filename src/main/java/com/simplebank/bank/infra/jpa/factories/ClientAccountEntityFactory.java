package com.simplebank.bank.infra.jpa.factories;

import com.simplebank.bank.infra.jpa.entities.AccountEntity;
import com.simplebank.bank.infra.jpa.entities.ClientAccountEntity;

public class ClientAccountEntityFactory extends AbstractAccountEntityFactory
{
  @Override
  public AccountEntity make()
  {
    return new ClientAccountEntity();
  }
}
