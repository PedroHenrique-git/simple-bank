package com.simplebank.bank;

import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.InvalidAmountException;
import com.simplebank.bank.domain.models.Account.BusinessAccount;
import com.simplebank.bank.domain.models.Account.ClientAccount;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Account tests")
public class AccountTests
{
  @Test
  void testClientAccountDeposit() throws InvalidAmountException
  {
    var account = new ClientAccount();

    account.deposit(100.0);

    assertEquals(100.0, account.getBalance());
    assertThrows(InvalidAmountException.class, () -> account.deposit(0.0));
    assertThrows(InvalidAmountException.class, () -> account.deposit(-100.0));
  }

  @Test
  void testClientAccountWithDraw() throws InvalidAmountException, AccountWithoutBalanceException
  {
    var account = new ClientAccount();

    account.deposit(100.0);
    account.withdraw(100.0);

    assertEquals(0.0, account.getBalance());
    assertThrows(AccountWithoutBalanceException.class, () -> account.withdraw(100.0));
  }

  @Test
  void testBusinessAccountDeposit() throws InvalidAmountException
  {
    var account = new BusinessAccount();

    account.deposit(100.0);

    assertEquals(100.0, account.getBalance());
    assertThrows(InvalidAmountException.class, () -> account.deposit(0.0));
    assertThrows(InvalidAmountException.class, () -> account.deposit(-100.0));
  }

  @Test
  void testBusinessAccountWithDraw() throws InvalidAmountException, AccountWithoutBalanceException
  {
    var account = new BusinessAccount();

    account.deposit(100.0);
    account.withdraw(100.0);

    assertEquals(0.0, account.getBalance());
    assertThrows(AccountWithoutBalanceException.class, () -> account.withdraw(100.0));
  }
}
