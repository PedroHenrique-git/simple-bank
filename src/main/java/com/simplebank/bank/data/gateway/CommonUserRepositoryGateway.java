package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.User.CommonUser;

public interface CommonUserRepositoryGateway {
    CommonUser save(CommonUser user);
    CommonUser update(CommonUser user);
    CommonUser find(long id);
    CommonUser delete(long id);
}
