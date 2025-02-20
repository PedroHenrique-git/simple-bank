package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.Transaction.Transaction;

public interface TransactionRepositoryGateway {
    Transaction save(Transaction user);
}
