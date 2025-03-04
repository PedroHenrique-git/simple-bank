package com.simplebank.bank.domain.models.Transaction;

import com.simplebank.bank.domain.models.Account.Account;

public class Transaction
{
  private long id;
  private Account payer;
  private Account payee;
  private double amount;

  public Transaction()
  {
  }

  public Transaction(long id, Account payer, Account payee, double amount)
  {
    this.id = id;
    this.payer = payer;
    this.payee = payee;
    this.amount = amount;
  }

  public long getId()
  {
    return this.id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public Account getPayer()
  {
    return payer;
  }

  public void setPayer(Account payer)
  {
    this.payer = payer;
  }

  public Account getPayee()
  {
    return payee;
  }

  public void setPayee(Account payee)
  {
    this.payee = payee;
  }

  public double getAmount()
  {
    return amount;
  }
}
