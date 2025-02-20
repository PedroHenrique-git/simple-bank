package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.Account.Account;

public interface AccountRepositoryGateway {
    Account save(Account account);
    Account find(long id);
    void delete(long id);
}
