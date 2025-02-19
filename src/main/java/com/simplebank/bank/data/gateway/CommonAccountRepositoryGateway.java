package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.Account.CommonAccount;

public interface CommonAccountRepositoryGateway {
    CommonAccount save(CommonAccount account);
    CommonAccount update(CommonAccount account);
    CommonAccount find(long id);
    CommonAccount delete(long id);
}
