package com.simplebank.bank.infra.jpa.factories;

import com.simplebank.bank.domain.DomainRegexMap;

public class UserEntityFactoryMaker
{
  public AbstractUserEntityFactory getFactory(String document)
  {
    if (document.matches(DomainRegexMap.CPF))
    {
      return new ClientUserEntityFactory();
    }

    return new BusinessUserEntityFactory();
  }
}
