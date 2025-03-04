package com.simplebank.bank.factories;

import com.simplebank.bank.domain.factories.BusinessUserFactory;
import com.simplebank.bank.domain.factories.ClientUserFactory;
import com.simplebank.bank.domain.factories.UserFactoryMaker;
import com.simplebank.bank.domain.models.User.BusinessUser;
import com.simplebank.bank.domain.models.User.ClientUser;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User factory tests")
public class UserFactoryTests
{
  public UserFactoryMaker maker;

  @BeforeEach
  void setup()
  {
    this.maker = new UserFactoryMaker();
  }

  @Test
  void testFactoryMaker()
  {
    var clientUserFactory = maker.getFactory("111.111.111-11");

    assertInstanceOf(ClientUserFactory.class, clientUserFactory);

    var businessUserFactory = maker.getFactory("11.111.111/1111-11");

    assertInstanceOf(BusinessUserFactory.class, businessUserFactory);
  }

  @Test
  void testClientUserFactory()
  {
    var clientUserFactory = maker.getFactory("111.111.111-11");

    assertInstanceOf(ClientUser.class, clientUserFactory.make());
  }

  @Test
  void testBusinessUserFactory()
  {
    var businessUserFactory = maker.getFactory("11.111.111/1111-11");

    assertInstanceOf(BusinessUser.class, businessUserFactory.make());
  }
}
