package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.Account.CommonAccount;

public interface WalletRepositoryGateway<T extends CommonAccount> {
    T save(T account);
    T update(T account);
    T get(long id);
    T delete(long id);
}
