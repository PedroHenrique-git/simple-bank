package com.simplebank.bank.usecases;

import com.simplebank.bank.domain.exceptions.InvalidCredentialsException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.InputValidator;

public class Login implements UseCase<AuthLoginDTORequest, AuthLoginDTOResponse>
{
  private final AuthManager authManager;
  private final InputValidator<AuthLoginDTORequest> validator;

  public Login(AuthManager authManager, InputValidator<AuthLoginDTORequest> validator)
  {
    this.authManager = authManager;
    this.validator = validator;
  }

  @Override
  public AuthLoginDTOResponse execute(AuthLoginDTORequest dto) throws UseCaseException
  {
    try
    {
      var violations = validator.validate(dto);

      if (!violations.isEmpty())
      {
        throw new UseCaseException("Invalid login input", violations);
      }

      var response = authManager.authenticate(dto);

      return new AuthLoginDTOResponse(response.commonToken(), response.refreshToken(),
          response.user());
    } catch (InvalidCredentialsException e)
    {
      throw new UseCaseException(e.getMessage());
    }
  }
}
