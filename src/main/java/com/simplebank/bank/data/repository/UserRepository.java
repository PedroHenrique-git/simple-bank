package com.simplebank.bank.data.repository;

import com.simplebank.bank.domain.model.User.User;

public interface UserRepository {
    User save(User user);
    User update(User user);
    User get(long id);
    User delete(long id);
}
