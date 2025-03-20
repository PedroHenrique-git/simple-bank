package com.simplebank.bank.usecases;

import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;

public class Login implements UseCase<AuthLoginDTORequest, AuthLoginDTOResponse>
{
  private final AuthManager authManager;

  public Login(AuthManager authManager)
  {
    this.authManager = authManager;
  }

  @Override
  public AuthLoginDTOResponse execute(AuthLoginDTORequest dto) throws UseCaseException
  {
    var token = authManager.authenticate(dto);

    return new AuthLoginDTOResponse(token.commonToken(), token.refreshToken());
  }
}
