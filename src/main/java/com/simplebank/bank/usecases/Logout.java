package com.simplebank.bank.usecases;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthLogoutDTORequest;
import com.simplebank.bank.usecases.ports.AuthLogoutDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;

public class Logout implements UseCase<AuthLogoutDTORequest, AuthLogoutDTOResponse>
{
  private final AuthManager authManager;

  public Logout(AuthManager authManager)
  {
    this.authManager = authManager;
  }

  @Override
  public AuthLogoutDTOResponse execute(AuthLogoutDTORequest dto)
      throws UseCaseException, ForbiddenException
  {
    authManager.logout();

    return new AuthLogoutDTOResponse("/login");
  }
}
