package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ValidationErrorException;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AccountDTORequest;
import com.simplebank.bank.usecases.ports.AccountDTOResponse;

public class CreateAccountOperation
    implements ControllerOperation<AccountDTOResponse, AccountDTORequest>
{
  private final UseCase<AccountDTORequest, AccountDTOResponse> useCase;

  public CreateAccountOperation(UseCase<AccountDTORequest, AccountDTOResponse> useCase)
  {
    this.useCase = useCase;
  }

  @Override
  public HttpResponse<AccountDTOResponse> execute(HttpRequest<AccountDTORequest> request)
      throws ValidationErrorException
  {
    var account = useCase.execute(request.body());

    return new HttpResponse<>(201, true, "account created successfully", account);
  }
}
