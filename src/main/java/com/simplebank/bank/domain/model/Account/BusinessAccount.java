package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.domain.model.User.CommonUser;

import java.util.List;

public class BusinessAccount extends CommonAccount {
    public BusinessAccount() {
        super();
    }

    public BusinessAccount(long id, double balance, CommonUser user, List<Transaction> transactions) {
        super(id, balance, user, transactions);
    }
}
