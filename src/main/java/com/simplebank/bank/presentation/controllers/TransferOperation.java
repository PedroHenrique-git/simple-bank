package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;

public class TransferOperation
    implements ControllerOperation<TransferDTOResponse, TransferDTORequest>
{
  private final UseCase<TransferDTORequest, TransferDTOResponse> useCase;

  public TransferOperation(UseCase<TransferDTORequest, TransferDTOResponse> useCase)
  {
    this.useCase = useCase;
  }

  @Override
  public HttpResponse<TransferDTOResponse> execute(HttpRequest<TransferDTORequest> request)
      throws UseCaseException, UnauthorizedException, ForbiddenException
  {
    var response = useCase.execute(request.body());

    return new HttpResponse<>(HttpStatus.SUCCESS.value(), true, "transfer made successfully",
        response);
  }
}
