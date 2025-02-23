package com.simplebank.bank.infra.jpa.factories;

import com.simplebank.bank.domain.DomainRegexMap;

public class AccountEntityFactoryMaker
{
  public AbstractAccountEntityFactory getFactory(String document)
  {
    if (document.matches(DomainRegexMap.CPF))
    {
      return new ClientAccountEntityFactory();
    }

    return new BusinessAccountEntityFactory();
  }
}
