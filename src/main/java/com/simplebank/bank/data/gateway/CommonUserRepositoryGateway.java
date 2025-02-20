package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.exception.*;
import com.simplebank.bank.domain.model.User.CommonUser;

public interface CommonUserRepositoryGateway {
    CommonUser save(CommonUser user);
    CommonUser update(long id, CommonUser user);
    CommonUser find(long id);
    void delete(long id);
}
