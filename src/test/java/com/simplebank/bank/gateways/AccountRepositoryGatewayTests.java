package com.simplebank.bank.gateways;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.mocks.AccountMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class AccountRepositoryGatewayTests
{
  @Autowired
  public AccountRepositoryGateway gateway;

  @Test
  void testCreateAccount()
  {
    var result = gateway.save(AccountMock.create());

    assertNotNull(result);
    assertNotNull(result.getUser());

    assertEquals("test-user@email.com", result.getUser().getEmail());
    assertEquals(0, result.getBalance());
  }

  @Test
  void testFindAccount()
  {
    var result = gateway.save(AccountMock.create());

    assertNotNull(gateway.find(result.getId()));
    assertNull(gateway.find(0L));
  }

  @Test
  void testUpdateAccount()
  {
    var result = gateway.save(AccountMock.create());

    result.setBalance(10000);

    var updatedAccount = gateway.update(result);

    assertNotNull(updatedAccount);
    assertEquals(10000, updatedAccount.getBalance());
  }

  @Test
  void testDeleteAccount()
  {
    var result = gateway.save(AccountMock.create());

    assertDoesNotThrow(() -> gateway.delete(result.getId()));
  }
}
