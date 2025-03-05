package com.simplebank.bank.gateways;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.data.gateways.UserRepositoryGateway;
import com.simplebank.bank.mocks.UserMock;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(value = {UserConfig.class, AccountConfig.class, TransactionConfig.class,
    CommonConfig.class})
public class UserRepositoryGatewayTests
{
  @Autowired
  public UserRepositoryGateway gateway;

  @Test
  void testCreateUser()
  {
    var result = gateway.save(UserMock.createClientUser());

    assertNotNull(result);
    assertEquals("test-user@email.com", result.getEmail());
  }

  @Test
  void testFindUser()
  {
    var result = gateway.save(UserMock.createClientUser());

    assertNotNull(gateway.find(result.getId()));
    assertNull(gateway.find(0L));
  }

  @Test
  void testUpdateAccount()
  {
    var result = gateway.save(UserMock.createClientUser());

    result.setEmail("test123@email.com");

    var updatedUser = gateway.update(result.getId(), result);

    assertNotNull(updatedUser);
    assertEquals("test123@email.com", updatedUser.getEmail());
  }

  @Test
  void testDeleteUser()
  {
    var result = gateway.save(UserMock.createClientUser());

    assertDoesNotThrow(() -> gateway.delete(result.getId()));
  }
}
