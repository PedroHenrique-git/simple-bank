package com.simplebank.bank.domain.models.Account;

import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.InvalidAmountException;
import com.simplebank.bank.domain.models.Transaction.Transaction;
import com.simplebank.bank.domain.models.User.User;
import java.util.List;

public class ClientAccount extends Account
{
  public ClientAccount()
  {
    super();
  }

  public ClientAccount(long id, double balance, User user, List<Transaction> payerTransactions,
                       List<Transaction> payeeTransactions)
  {
    super(id, balance, user, payerTransactions, payeeTransactions);
  }

  public double transfer(double amount, Account payee)
      throws AccountWithoutBalanceException, InvalidAmountException
  {
    withdraw(amount);

    return payee.deposit(amount);
  }
}
