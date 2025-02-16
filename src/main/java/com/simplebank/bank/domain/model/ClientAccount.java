package com.simplebank.bank.domain.model;

import com.simplebank.bank.domain.exception.AccountWithoutBalanceException;
import com.simplebank.bank.domain.exception.InvalidAmountException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientAccount extends Account {
    public double transfer(double amount, Account payee) throws AccountWithoutBalanceException, InvalidAmountException {
        withdraw(amount);

        return payee.deposit(amount);
    }
}
