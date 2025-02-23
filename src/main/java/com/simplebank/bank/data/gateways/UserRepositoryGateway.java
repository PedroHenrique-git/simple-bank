package com.simplebank.bank.data.gateways;

import com.simplebank.bank.domain.models.User.User;

public interface UserRepositoryGateway
{
  User save(User user);

  User update(long id, User user);

  User find(long id);

  void delete(long id);
}
