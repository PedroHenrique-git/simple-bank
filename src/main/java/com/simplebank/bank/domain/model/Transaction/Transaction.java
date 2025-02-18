package com.simplebank.bank.domain.model.Transaction;

import com.simplebank.bank.domain.model.Account.CommonAccount;

public class Transaction {
    private CommonAccount payer;
    private CommonAccount payee;
    private double value;

    public Transaction() {}

    public Transaction(CommonAccount payer, CommonAccount payee, double value) {
        this.payer = payer;
        this.payee = payee;
        this.value = value;
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
