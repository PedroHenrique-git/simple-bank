package com.simplebank.bank.mocks;

import com.simplebank.bank.domain.models.Account.Account;
import com.simplebank.bank.domain.models.Account.ClientAccount;
import java.util.List;

public class AccountMock
{
  public static Account create()
  {
    var account = new ClientAccount();

    account.setUser(UserMock.createClientUser());
    account.setPayeeTransactions(List.of());
    account.setPayerTransactions(List.of());

    return account;
  }
}
