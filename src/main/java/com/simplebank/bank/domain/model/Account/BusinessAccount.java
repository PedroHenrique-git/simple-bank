package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.domain.model.User.CommonUser;

import java.util.List;

public class BusinessAccount extends CommonAccount {
    public BusinessAccount() {
        super();
    }

    public BusinessAccount(long id, double balance, CommonUser user, List<Transaction> payerTransactions, List<Transaction> payeeTransactions) {
        super(id, balance, user, payerTransactions, payeeTransactions);
    }
}
