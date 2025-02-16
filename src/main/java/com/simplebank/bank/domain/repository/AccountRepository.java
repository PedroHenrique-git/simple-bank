package com.simplebank.bank.domain.repository;

import com.simplebank.bank.domain.model.Account;

public interface AccountRepository {
    Account save(Account account);
    Account update(Account account);
    Account get(long id);
    Account delete(long id);
}
