package com.simplebank.bank.data.gateways;

import com.simplebank.bank.domain.models.Transaction.Transaction;

public interface TransactionRepositoryGateway
{
  Transaction save(Transaction user);
}
