package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.exception.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exception.InvalidAmountException;

public abstract class Account {
    protected long id;
    protected double balance;

    public Account() {}

    public Account(long id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public double withdraw(double amount) throws AccountWithoutBalanceException {
        if(balance < amount) {
            throw new AccountWithoutBalanceException("There is no balance available to carry out the operation");
        }

        return balance -= amount;
    }

    public double deposit(double amount) throws InvalidAmountException {
        if(amount <= 0) {
            throw new InvalidAmountException("The amount must be a positive value greater than zero");
        }

        return balance += amount;
    }
}
