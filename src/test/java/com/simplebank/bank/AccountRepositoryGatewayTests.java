package com.simplebank.bank;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.models.Account.ClientAccount;
import com.simplebank.bank.domain.models.User.ClientUser;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(value = {UserConfig.class, AccountConfig.class, TransactionConfig.class,
    CommonConfig.class})
public class AccountRepositoryGatewayTests
{
  @Autowired
  public AccountRepositoryGateway gateway;

  @Test
  void testCreateAccount()
  {
    var account = new ClientAccount();
    var user = new ClientUser();

    user.setName("Test");
    user.setPassword("123");
    user.setEmail("t@email.com");
    user.setDocument("111.111.111-11");

    account.setUser(user);
    account.setPayeeTransactions(List.of());
    account.setPayerTransactions(List.of());

    var result = gateway.save(account);

    assertNotNull(result);
    assertNotNull(result.getUser());

    assertEquals("t@email.com", result.getUser().getEmail());
    assertEquals(0, result.getBalance());
  }
}
