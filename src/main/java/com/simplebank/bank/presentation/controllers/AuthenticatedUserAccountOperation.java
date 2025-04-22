package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTORequest;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTOResponse;

public class AuthenticatedUserAccountOperation implements
    ControllerOperation<AuthenticatedUserAccountDTOResponse, AuthenticatedUserAccountDTORequest>
{
  private final UseCase<AuthenticatedUserAccountDTORequest, AuthenticatedUserAccountDTOResponse>
      usecase;

  public AuthenticatedUserAccountOperation(
      UseCase<AuthenticatedUserAccountDTORequest, AuthenticatedUserAccountDTOResponse> usecase)
  {
    this.usecase = usecase;
  }

  @Override
  public HttpResponse<AuthenticatedUserAccountDTOResponse> execute(
      HttpRequest<AuthenticatedUserAccountDTORequest> request)
      throws ForbiddenException, UseCaseException, UnauthorizedException
  {
    var response = usecase.execute(request.body());

    return new HttpResponse<>(HttpStatus.SUCCESS.value(), true,
        "account data successfully obtained",
        response);
  }
}
