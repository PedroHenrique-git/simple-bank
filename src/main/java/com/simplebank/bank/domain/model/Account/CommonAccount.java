package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.exception.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exception.InvalidAmountException;
import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.domain.model.User.CommonUser;

import java.util.List;

public abstract class CommonAccount {
    protected long id;
    protected double balance;
    protected CommonUser user;
    protected List<Transaction> transactions;

    public CommonAccount() {}

    public CommonAccount(long id, double balance, CommonUser user, List<Transaction> transactions) {
        this.id = id;
        this.balance = balance;
        this.user = user;
        this.transactions = transactions;
    }

    public void setUser(CommonUser user) {
        this.user = user;
    }

    public CommonUser getUser() {
        return user;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
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
