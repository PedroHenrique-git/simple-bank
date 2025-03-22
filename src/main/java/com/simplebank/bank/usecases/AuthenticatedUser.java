package com.simplebank.bank.usecases;

import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTORequest;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;

public class AuthenticatedUser
    implements UseCase<AuthAuthenticatedUserDTORequest, AuthAuthenticatedUserDTOResponse>
{
  private final AuthManager authManager;

  public AuthenticatedUser(AuthManager authManager)
  {
    this.authManager = authManager;
  }

  @Override
  public AuthAuthenticatedUserDTOResponse execute(AuthAuthenticatedUserDTORequest dto)
      throws UseCaseException
  {
    var authenticatedUser = authManager.getAuthenticatedUser();

    return new AuthAuthenticatedUserDTOResponse(authenticatedUser.getId(),
        authenticatedUser.getName(), authenticatedUser.getEmail());
  }
}
