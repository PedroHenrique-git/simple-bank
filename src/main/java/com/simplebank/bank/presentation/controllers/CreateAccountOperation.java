package com.simplebank.bank.presentation.controllers;

import com.simplebank.bank.domain.exceptions.ValidationErrorException;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.presentation.controllers.ports.HttpResponse;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;

public class CreateAccountOperation
    implements ControllerOperation<CreateAccountDTOResponse, CreateAccountDTORequest>
{
  private final UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> useCase;

  public CreateAccountOperation(UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> useCase)
  {
    this.useCase = useCase;
  }

  @Override
  public HttpResponse<CreateAccountDTOResponse> execute(
      HttpRequest<CreateAccountDTORequest> request)
      throws ValidationErrorException
  {
    var account = useCase.execute(request.body());

    return new HttpResponse<>(201, true, "account created successfully", account);
  }
}
