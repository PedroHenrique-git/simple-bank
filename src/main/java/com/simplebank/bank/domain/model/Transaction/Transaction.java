package com.simplebank.bank.domain.model.Transaction;

import com.simplebank.bank.domain.model.Account.CommonAccount;

public class Transaction {
    private long id;
    private CommonAccount payer;
    private CommonAccount payee;
    private double value;

    public Transaction() {}

    public Transaction(long id, CommonAccount payer, CommonAccount payee, double value) {
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

    public void setPayer(CommonAccount payer) {
        this.payer = payer;
    }

    public CommonAccount getPayer() {
        return payer;
    }

    public void setPayee(CommonAccount payee) {
        this.payee = payee;
    }

    public CommonAccount getPayee() {
        return payee;
    }

    public double getValue() {
        return value;
    }
}
