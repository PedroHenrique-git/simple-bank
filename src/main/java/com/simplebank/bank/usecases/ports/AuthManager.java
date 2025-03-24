package com.simplebank.bank.usecases.ports;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.InvalidCredentialsException;
import com.simplebank.bank.domain.models.User.User;

public interface AuthManager
{
  TokenDTO authenticate(AuthLoginDTORequest dto) throws InvalidCredentialsException;

  User getAuthenticatedUser();

  boolean isAuthorized(long userId) throws ForbiddenException;
}
