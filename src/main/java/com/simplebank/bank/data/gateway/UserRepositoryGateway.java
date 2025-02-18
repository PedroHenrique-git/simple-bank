package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.User.CommonUser;

public interface UserRepositoryGateway<T extends CommonUser> {
    T save(T user);
    T update(T user);
    T get(long id);
    T delete(long id);
}
