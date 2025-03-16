package com.simplebank.bank.gateways;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.data.gateways.TransactionRepositoryGateway;
import com.simplebank.bank.mocks.AccountMock;
import com.simplebank.bank.mocks.TransactionMock;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
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

    var acTwoMock = AccountMock.create();

    acTwoMock.getUser().setEmail("test-user2@email.com");
    acTwoMock.getUser().setDocument("222.222.222-22");

    var acTwo = accountGateway.save(acTwoMock);

    transaction.setPayer(acOne);
    transaction.setPayee(acTwo);

    var result = gateway.save(transaction);

    assertNotNull(result);
  }
}
