package com.simplebank.bank.domain.models.Account;

import com.simplebank.bank.domain.exceptions.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exceptions.InvalidAmountException;
import com.simplebank.bank.domain.models.Transaction.Transaction;
import com.simplebank.bank.domain.models.User.User;
import java.util.List;

public abstract class Account
{
  protected long id;
  protected double balance;
  protected User user;
  protected List<Transaction> payerTransactions;
  protected List<Transaction> payeeTransactions;

  protected Account()
  {
  }

  protected Account(long id, double balance, User user, List<Transaction> payerTransactions,
                    List<Transaction> payeeTransactions)
  {
    this.id = id;
    this.balance = balance;
    this.user = user;
    this.payerTransactions = payerTransactions;
    this.payeeTransactions = payeeTransactions;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public List<Transaction> getPayerTransactions()
  {
    return this.payerTransactions;
  }

  public void setPayerTransactions(List<Transaction> payerTransactions)
  {
    this.payerTransactions = payerTransactions;
  }

  public List<Transaction> getPayeeTransactions()
  {
    return this.payeeTransactions;
  }

  public void setPayeeTransactions(List<Transaction> payeeTransactions)
  {
    this.payeeTransactions = payeeTransactions;
  }

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public double getBalance()
  {
    return balance;
  }

  public void setBalance(double balance)
  {
    this.balance = balance;
  }

  public double withdraw(Double amount) throws AccountWithoutBalanceException
  {
    if (balance < amount)
    {
      throw new AccountWithoutBalanceException(
          "There is no balance available to carry out the operation");
    }

    return balance -= amount;
  }

  public double deposit(Double amount) throws InvalidAmountException
  {
    if (amount <= 0)
    {
      throw new InvalidAmountException("The amount must be a positive value greater than zero");
    }

    return balance += amount;
  }
}
