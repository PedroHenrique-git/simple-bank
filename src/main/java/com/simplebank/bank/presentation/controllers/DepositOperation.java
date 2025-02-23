package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ValidationErrorException;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;

public class DepositOperation implements ControllerOperation<DepositDTOResponse, DepositDTORequest>
{
  private final UseCase<DepositDTORequest, DepositDTOResponse> useCase;

  public DepositOperation(UseCase<DepositDTORequest, DepositDTOResponse> useCase)
  {
    this.useCase = useCase;
  }

  @Override
  public HttpResponse<DepositDTOResponse> execute(HttpRequest<DepositDTORequest> request)
      throws ValidationErrorException
  {
    var response = useCase.execute(request.body());

    return new HttpResponse<>(200, true, "deposit made successfully", response);
  }
}
