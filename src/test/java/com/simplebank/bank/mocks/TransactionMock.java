package com.simplebank.bank.mocks;

import com.simplebank.bank.domain.models.Transaction.Transaction;

public class TransactionMock
{
  public static Transaction create()
  {
    return new Transaction();
  }
}
