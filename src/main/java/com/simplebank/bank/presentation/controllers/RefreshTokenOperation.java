package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.RefreshAuthDTORequest;
import com.simplebank.bank.usecases.ports.RefreshAuthDTOResponse;

public class RefreshTokenOperation
    implements ControllerOperation<RefreshAuthDTOResponse, RefreshAuthDTORequest>
{
  public UseCase<RefreshAuthDTORequest, RefreshAuthDTOResponse> usecase;

  public RefreshTokenOperation(UseCase<RefreshAuthDTORequest, RefreshAuthDTOResponse> usecase)
  {
    this.usecase = usecase;
  }

  @Override
  public HttpResponse<RefreshAuthDTOResponse> execute(HttpRequest<RefreshAuthDTORequest> request)
      throws ForbiddenException, UseCaseException, UnauthorizedException
  {
    var response = usecase.execute(request.body());

    return new HttpResponse<>(HttpStatus.SUCCESS.value(), true,
        "Token updated successfully",
        response);
  }
}
