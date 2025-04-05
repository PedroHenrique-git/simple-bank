package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthLogoutDTORequest;
import com.simplebank.bank.usecases.ports.AuthLogoutDTOResponse;

public class LogoutOperation
    implements ControllerOperation<AuthLogoutDTOResponse, AuthLogoutDTORequest>
{
  private final UseCase<AuthLogoutDTORequest, AuthLogoutDTOResponse> usecase;

  public LogoutOperation(UseCase<AuthLogoutDTORequest, AuthLogoutDTOResponse> usecase)
  {
    this.usecase = usecase;
  }

  @Override
  public HttpResponse<AuthLogoutDTOResponse> execute(HttpRequest<AuthLogoutDTORequest> request)
      throws ForbiddenException, UseCaseException
  {
    var response = usecase.execute(request.body());

    return new HttpResponse<>(HttpStatus.SUCCESS.value(), true,
        "You have been logged out successfully.",
        response);
  }
}
