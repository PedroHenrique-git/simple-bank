package com.simplebank.bank.data.gateway;

import com.simplebank.bank.domain.model.User.User;

public interface UserRepositoryGateway {
    User save(User user);
    User update(long id, User user);
    User find(long id);
    void delete(long id);
}
