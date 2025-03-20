package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;

public class LoginOperation
    implements ControllerOperation<AuthLoginDTOResponse, AuthLoginDTORequest>
{
  private final UseCase<AuthLoginDTORequest, AuthLoginDTOResponse> usecase;


  public LoginOperation(UseCase<AuthLoginDTORequest, AuthLoginDTOResponse> usecase)
  {
    this.usecase = usecase;
  }

  @Override
  public HttpResponse<AuthLoginDTOResponse> execute(HttpRequest<AuthLoginDTORequest> request)
      throws UseCaseException
  {
    var response = usecase.execute(request.body());

    return new HttpResponse<>(HttpStatus.SUCCESS.value(), true, "login successful",
        response);
  }
}
