package com.simplebank.bank.usecases.ports;

import com.simplebank.bank.domain.models.User.User;

public interface AuthManager
{
  TokenDTO authenticate(AuthLoginDTORequest dto);

  User getAuthenticatedUser();
}
