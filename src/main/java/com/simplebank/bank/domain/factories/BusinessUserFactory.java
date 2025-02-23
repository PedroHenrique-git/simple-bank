package com.simplebank.bank.domain.factories;

import com.simplebank.bank.domain.models.User.BusinessUser;
import com.simplebank.bank.domain.models.User.User;

public class BusinessUserFactory extends AbstractUserFactory
{

  @Override
  public User make()
  {
    return new BusinessUser();
  }
}
