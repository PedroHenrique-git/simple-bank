package com.simplebank.bank.domain.models.Account;

import com.simplebank.bank.domain.models.Transaction.Transaction;
import com.simplebank.bank.domain.models.User.User;
import java.util.List;

public class BusinessAccount extends Account
{
  public BusinessAccount()
  {
    super();
  }

  public BusinessAccount(long id, double balance, User user, List<Transaction> payerTransactions,
                         List<Transaction> payeeTransactions)
  {
    super(id, balance, user, payerTransactions, payeeTransactions);
  }
}
