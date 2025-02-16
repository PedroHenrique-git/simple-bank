package com.simplebank.bank.domain.exception;

public class AccountRepositoryException extends RuntimeException {
    public AccountRepositoryException(String message) {
        super(message);
    }
}
