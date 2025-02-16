package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.exception.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exception.InvalidAmountException;

public class ClientAccount extends Account {
    public ClientAccount() {
        super();
    }

    public ClientAccount(long id, double balance) {
        super(id, balance);
    }

    public double transfer(double amount, Account payee) throws AccountWithoutBalanceException, InvalidAmountException {
        withdraw(amount);

        return payee.deposit(amount);
    }
}
