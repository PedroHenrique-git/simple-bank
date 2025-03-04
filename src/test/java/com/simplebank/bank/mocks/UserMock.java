package com.simplebank.bank.mocks;

import com.simplebank.bank.domain.models.User.BusinessUser;
import com.simplebank.bank.domain.models.User.ClientUser;
import com.simplebank.bank.domain.models.User.User;

public class UserMock
{
  public static User createClientUser()
  {
    var clientUser = new ClientUser();

    clientUser.setEmail("test-user@email.com");
    clientUser.setPassword("abc123");
    clientUser.setName("test");
    clientUser.setDocument("111.111.111-11");

    return clientUser;
  }

  public static User createBusinessUser()
  {
    var businessUser = new BusinessUser();

    businessUser.setEmail("test-business@email.com");
    businessUser.setPassword("abc123");
    businessUser.setName("test");
    businessUser.setDocument("11.111.111/1111-11");

    return businessUser;
  }
}
