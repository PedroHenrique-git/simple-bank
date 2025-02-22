package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.exception.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exception.InvalidAmountException;
import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.domain.model.User.User;
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
