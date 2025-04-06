package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTORequest;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTOResponse;

public class AuthenticatedUserOperation
    implements
    ControllerOperation<AuthAuthenticatedUserDTOResponse, AuthAuthenticatedUserDTORequest>
{
  private final UseCase<AuthAuthenticatedUserDTORequest, AuthAuthenticatedUserDTOResponse> usecase;

  public AuthenticatedUserOperation(
      UseCase<AuthAuthenticatedUserDTORequest, AuthAuthenticatedUserDTOResponse> usecase)
  {
    this.usecase = usecase;
  }

  @Override
  public HttpResponse<AuthAuthenticatedUserDTOResponse> execute(
      HttpRequest<AuthAuthenticatedUserDTORequest> request)
      throws UseCaseException, UnauthorizedException, ForbiddenException
  {
    var response = usecase.execute(request.body());

    return new HttpResponse<>(HttpStatus.SUCCESS.value(), true,
        "authenticated user data successfully obtained",
        response);
  }
}
