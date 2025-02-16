package com.simplebank.bank.domain.exception;

public class AccountWithoutBalanceException extends Exception {
    public AccountWithoutBalanceException(String message) {
        super(message);
    }
}
