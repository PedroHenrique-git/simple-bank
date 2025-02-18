package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.exception.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exception.InvalidAmountException;
import com.simplebank.bank.domain.model.User.CommonUser;

public class ClientAccount extends CommonAccount {
    public ClientAccount() {
        super();
    }

    public ClientAccount(long id, double balance, CommonUser user) {
        super(id, balance, user);
    }

    public double transfer(double amount, CommonAccount payee) throws AccountWithoutBalanceException, InvalidAmountException {
        withdraw(amount);

        return payee.deposit(amount);
    }
}
