package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.domain.model.User.CommonUser;

public interface TransactionRepositoryGateway {
    Transaction save(Transaction user);
}
