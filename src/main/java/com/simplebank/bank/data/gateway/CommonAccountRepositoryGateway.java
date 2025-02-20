package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.Account.CommonAccount;

public interface CommonAccountRepositoryGateway {
    CommonAccount save(CommonAccount account);
    CommonAccount find(long id);
    void delete(long id);
}
