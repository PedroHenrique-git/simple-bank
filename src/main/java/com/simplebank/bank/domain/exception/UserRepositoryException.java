package com.simplebank.bank.domain.exception;

public class UserRepositoryException extends RuntimeException {
    public UserRepositoryException(String message) {
        super(message);
    }
}
