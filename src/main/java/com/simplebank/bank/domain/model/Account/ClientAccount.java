package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.exception.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exception.InvalidAmountException;
import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.domain.model.User.CommonUser;

import java.util.List;

public class ClientAccount extends CommonAccount {
    public ClientAccount() {
        super();
    }

    public ClientAccount(long id, double balance, CommonUser user, List<Transaction> transactions) {
        super(id, balance, user, transactions);
    }

    public double transfer(double amount, CommonAccount payee) throws AccountWithoutBalanceException, InvalidAmountException {
        withdraw(amount);

        return payee.deposit(amount);
    }
}
