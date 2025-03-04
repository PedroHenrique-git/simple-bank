package com.simplebank.bank.gateways;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.data.gateways.TransactionRepositoryGateway;
import com.simplebank.bank.mocks.AccountMock;
import com.simplebank.bank.mocks.TransactionMock;
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
public class TransactionRepositoryGatewayTests
{
  @Autowired
  public TransactionRepositoryGateway gateway;

  @Autowired
  public AccountRepositoryGateway accountGateway;

  @Test
  void testCreateTransaction()
  {
    var transaction = TransactionMock.create();

    var acOne = accountGateway.save(AccountMock.create());
    var acTwo = accountGateway.save(AccountMock.create());

    transaction.setPayer(acOne);
    transaction.setPayee(acTwo);

    var result = gateway.save(transaction);

    assertNotNull(result);
  }
}
