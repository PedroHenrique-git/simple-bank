package com.simplebank.bank.usecases;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.InvalidToken;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.RefreshAuthDTORequest;
import com.simplebank.bank.usecases.ports.RefreshAuthDTOResponse;

public class RefreshToken implements UseCase<RefreshAuthDTORequest, RefreshAuthDTOResponse>
{
  private final AuthManager authManager;

  public RefreshToken(AuthManager authManager)
  {
    this.authManager = authManager;
  }

  @Override
  public RefreshAuthDTOResponse execute(RefreshAuthDTORequest dto)
      throws UseCaseException, UnauthorizedException, ForbiddenException
  {
    try
    {
      return authManager.refreshAuthentication(dto);
    } catch (InvalidToken e)
    {
      throw new UseCaseException(e.getMessage());
    }
  }
}
