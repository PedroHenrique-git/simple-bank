package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.exception.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exception.InvalidAmountException;
import com.simplebank.bank.domain.model.Transaction.Transaction;
import com.simplebank.bank.domain.model.User.User;

import java.util.List;

public abstract class Account {
    protected long id;
    protected double balance;
    protected User user;
    protected List<Transaction> payerTransactions;
    protected List<Transaction> payeeTransactions;

    public Account() {}

    public Account(long id, double balance, User user, List<Transaction> payerTransactions, List<Transaction> payeeTransactions) {
        this.id = id;
        this.balance = balance;
        this.user = user;
        this.payerTransactions = payerTransactions;
        this.payeeTransactions = payeeTransactions;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setPayerTransactions(List<Transaction> payerTransactions) {
        this.payerTransactions = payerTransactions;
    }

    public List<Transaction> getPayerTransactions() {
        return this.payerTransactions;
    }

    public void setPayeeTransactions(List<Transaction> payeeTransactions) {
        this.payeeTransactions = payeeTransactions;
    }

    public List<Transaction> getPayeeTransactions() {
        return this.payeeTransactions;
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
