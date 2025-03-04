package com.simplebank.bank;

import com.simplebank.bank.domain.factories.AccountFactoryMaker;
import com.simplebank.bank.domain.factories.BusinessAccountFactory;
import com.simplebank.bank.domain.factories.ClientAccountFactory;
import com.simplebank.bank.domain.models.Account.BusinessAccount;
import com.simplebank.bank.domain.models.Account.ClientAccount;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Account factory tests")
public class AccountFactoryTests
{
  public AccountFactoryMaker maker;

  @BeforeEach
  void setup()
  {
    this.maker = new AccountFactoryMaker();
  }

  @Test
  void testFactoryMaker()
  {
    var clientAccountFactory = maker.getFactory("111.111.111-11");

    assertInstanceOf(ClientAccountFactory.class, clientAccountFactory);

    var businessAccountFactory = maker.getFactory("11.111.111/1111-11");

    assertInstanceOf(BusinessAccountFactory.class, businessAccountFactory);
  }

  @Test
  void testClientAccountFactory()
  {
    var clientAccountFactory = maker.getFactory("111.111.111-11");

    assertInstanceOf(ClientAccount.class, clientAccountFactory.make());
  }

  @Test
  void testBusinessAccountFactory()
  {
    var businessAccountFactory = maker.getFactory("11.111.111/1111-11");

    assertInstanceOf(BusinessAccount.class, businessAccountFactory.make());
  }
}
