package com.simplebank.bank.domain.factories;

import com.simplebank.bank.domain.DomainRegexMap;

public class AccountFactoryMaker
{
  public AbstractAccountFactory getFactory(String document)
  {
    if (document.matches(DomainRegexMap.CPF))
    {
      return new ClientAccountFactory();
    }

    return new BusinessAccountFactory();
  }
}
