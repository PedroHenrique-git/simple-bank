package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.domain.model.User.User;

import java.util.List;

public class BusinessAccount extends Account {
    public BusinessAccount() {
        super();
    }

    public BusinessAccount(long id, double balance, User user, List<Transaction> payerTransactions, List<Transaction> payeeTransactions) {
        super(id, balance, user, payerTransactions, payeeTransactions);
    }
}
