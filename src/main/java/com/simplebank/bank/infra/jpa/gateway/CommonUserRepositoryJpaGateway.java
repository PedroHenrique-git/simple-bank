package com.simplebank.bank.infra.jpa.gateway;

import com.simplebank.bank.data.gateway.CommonUserRepositoryGateway;
import com.simplebank.bank.domain.model.User.CommonUser;

public class CommonUserRepositoryJpaGateway implements CommonUserRepositoryGateway {
    @Override
    public CommonUser save(CommonUser user) {
        return null;
    }

    @Override
    public CommonUser update(CommonUser user) {
        return null;
    }

    @Override
    public CommonUser find(long id) {
        return null;
    }

    @Override
    public CommonUser delete(long id) {
        return null;
    }
}
