package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.exceptions.ValidationErrorException;
import com.simplebank.bank.usecases.mapper.CreateAccountDTOMapper;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.Encoder;
import com.simplebank.bank.usecases.ports.InputValidator;

public class CreateAccount implements UseCase<CreateAccountDTORequest, CreateAccountDTOResponse>
{
  private final AccountRepositoryGateway repository;
  private final InputValidator<CreateAccountDTORequest> validator;
  private final CreateAccountDTOMapper mapper;
  private final Encoder encoder;

  public CreateAccount(AccountRepositoryGateway repository,
                       InputValidator<CreateAccountDTORequest> validator,
                       CreateAccountDTOMapper mapper, Encoder encoder)
  {
    this.repository = repository;
    this.validator = validator;
    this.mapper = mapper;
    this.encoder = encoder;
  }

  @Override
  public CreateAccountDTOResponse execute(CreateAccountDTORequest dto)
      throws ValidationErrorException
  {
    var violations = validator.validate(dto);

    if (!violations.isEmpty())
    {
      throw new ValidationErrorException("Invalid User input", violations);
    }

    var model = mapper.toAccount(dto);

    model.getUser().setPassword(encoder.encode(dto.password()));

    var createdAccount = repository.save(model);

    return mapper.toResponse(createdAccount);
  }
}
