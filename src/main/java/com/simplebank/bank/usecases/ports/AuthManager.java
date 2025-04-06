package com.simplebank.bank.usecases.ports;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.InvalidCredentialsException;
import com.simplebank.bank.domain.exceptions.InvalidToken;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.models.User.User;

public interface AuthManager
{
  RefreshAuthDTOResponse refreshAuthentication(RefreshAuthDTORequest dto)
      throws InvalidToken, UnauthorizedException;

  TokenDTO authenticate(AuthLoginDTORequest dto) throws InvalidCredentialsException;

  User getAuthenticatedUser();

  boolean isAuthorized(long userId) throws ForbiddenException;

  boolean logout();
}
