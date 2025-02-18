package com.simplebank.bank.domain.model.Account;

import com.simplebank.bank.domain.model.User.CommonUser;

public class BusinessAccount extends CommonAccount {
    public BusinessAccount() {
        super();
    }

    public BusinessAccount(long id, double balance, CommonUser user) {
        super(id, balance, user);
    }
}
