package com.simplebank.bank.domain.model.Transaction;

import com.simplebank.bank.domain.model.Account.Account;

public class Transaction {
    private long id;
    private Account payer;
    private Account payee;
    private double value;

    public Transaction() {}

    public Transaction(long id, Account payer, Account payee, double value) {
        this.id = id;
        this.payer = payer;
        this.payee = payee;
        this.value = value;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPayer(Account payer) {
        this.payer = payer;
    }

    public Account getPayer() {
        return payer;
    }

    public void setPayee(Account payee) {
        this.payee = payee;
    }

    public Account getPayee() {
        return payee;
    }

    public double getValue() {
        return value;
    }
}
