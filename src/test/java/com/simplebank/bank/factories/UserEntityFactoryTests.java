package com.simplebank.bank.factories;

import com.simplebank.bank.infra.jpa.entities.BusinessUserEntity;
import com.simplebank.bank.infra.jpa.entities.ClientUserEntity;
import com.simplebank.bank.infra.jpa.factories.BusinessUserEntityFactory;
import com.simplebank.bank.infra.jpa.factories.ClientUserEntityFactory;
import com.simplebank.bank.infra.jpa.factories.UserEntityFactoryMaker;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User entity factory tests")
public class UserEntityFactoryTests
{
  public UserEntityFactoryMaker maker;

  @BeforeEach
  void setup()
  {
    this.maker = new UserEntityFactoryMaker();
  }

  @Test
  void testFactoryMaker()
  {
    var clientUserEntityFactory = maker.getFactory("111.111.111-11");

    assertInstanceOf(ClientUserEntityFactory.class, clientUserEntityFactory);

    var businessUserEntityFactory = maker.getFactory("11.111.111/1111-11");

    assertInstanceOf(BusinessUserEntityFactory.class, businessUserEntityFactory);
  }

  @Test
  void testClientUserEntityFactory()
  {
    var clientUserEntityFactory = maker.getFactory("111.111.111-11");

    assertInstanceOf(ClientUserEntity.class, clientUserEntityFactory.make());
  }

  @Test
  void testBusinessUserEntityFactory()
  {
    var businessUserEntityFactory = maker.getFactory("11.111.111/1111-11");

    assertInstanceOf(BusinessUserEntity.class, businessUserEntityFactory.make());
  }
}
