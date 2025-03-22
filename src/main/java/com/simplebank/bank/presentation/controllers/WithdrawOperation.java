package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.WithdrawDTORequest;
import com.simplebank.bank.usecases.ports.WithdrawDTOResponse;

public class WithdrawOperation
    implements ControllerOperation<WithdrawDTOResponse, WithdrawDTORequest>
{
  private final UseCase<WithdrawDTORequest, WithdrawDTOResponse> usecase;

  public WithdrawOperation(UseCase<WithdrawDTORequest, WithdrawDTOResponse> usecase)
  {
    this.usecase = usecase;
  }

  @Override
  public HttpResponse<WithdrawDTOResponse> execute(HttpRequest<WithdrawDTORequest> request)
      throws UseCaseException, ForbiddenException
  {
    var response = usecase.execute(request.body());

    return new HttpResponse<>(HttpStatus.SUCCESS.value(), true, "withdraw made successfully",
        response);
  }
}
