package com.simplebank.bank;

import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.InvalidAmountException;
import com.simplebank.bank.domain.models.Account.BusinessAccount;
import com.simplebank.bank.domain.models.Account.ClientAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Client account tests")
public class ClientAccountTests
{
  @Test
  void testTransfer() throws InvalidAmountException, AccountWithoutBalanceException
  {
    var payer = new ClientAccount();

    payer.setBalance(1000);

    var payee = new BusinessAccount();

    payer.transfer(1000, payee);

    assertEquals(1000, payee.getBalance());
    assertThrows(AccountWithoutBalanceException.class, () -> payer.transfer(1000, payee));
  }
}
