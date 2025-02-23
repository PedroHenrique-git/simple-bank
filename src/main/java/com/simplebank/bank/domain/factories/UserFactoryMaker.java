package com.simplebank.bank.domain.factories;

import com.simplebank.bank.domain.DomainRegexMap;

public class UserFactoryMaker
{
  public AbstractUserFactory getFactory(String document)
  {
    if (document.matches(DomainRegexMap.CPF))
    {
      return new ClientUserFactory();
    }

    return new BusinessUserFactory();
  }
}
