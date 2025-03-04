package com.simplebank.bank;

import com.simplebank.bank.infra.jpa.entities.BusinessAccountEntity;
import com.simplebank.bank.infra.jpa.entities.ClientAccountEntity;
import com.simplebank.bank.infra.jpa.factories.AccountEntityFactoryMaker;
import com.simplebank.bank.infra.jpa.factories.BusinessAccountEntityFactory;
import com.simplebank.bank.infra.jpa.factories.ClientAccountEntityFactory;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Account entity factory tests")
public class AccountEntityFactoryTests
{
  public AccountEntityFactoryMaker maker;

  @BeforeEach
  void setup()
  {
    this.maker = new AccountEntityFactoryMaker();
  }

  @Test
  void testFactoryMaker()
  {
    var clientAccountEntityFactory = maker.getFactory("111.111.111-11");

    assertInstanceOf(ClientAccountEntityFactory.class, clientAccountEntityFactory);

    var businessAccountEntityFactory = maker.getFactory("11.111.111/1111-11");

    assertInstanceOf(BusinessAccountEntityFactory.class, businessAccountEntityFactory);
  }

  @Test
  void testClientAccountEntityFactory()
  {
    var clientAccountEntityFactory = maker.getFactory("111.111.111-11");

    assertInstanceOf(ClientAccountEntity.class, clientAccountEntityFactory.make());
  }

  @Test
  void testBusinessAccountEntityFactory()
  {
    var businessAccountEntityFactory = maker.getFactory("11.111.111/1111-11");

    assertInstanceOf(BusinessAccountEntity.class, businessAccountEntityFactory.make());
  }
}
