package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.exceptions.ValidationErrorException;
import com.simplebank.bank.usecases.mapper.AccountDTOMapper;
import com.simplebank.bank.usecases.ports.AccountDTORequest;
import com.simplebank.bank.usecases.ports.AccountDTOResponse;
import com.simplebank.bank.usecases.ports.CreateAccountInputValidator;
import com.simplebank.bank.usecases.ports.Encoder;

public class CreateAccount implements UseCase<AccountDTORequest, AccountDTOResponse>
{
  private final AccountRepositoryGateway repository;
  private final CreateAccountInputValidator validator;
  private final AccountDTOMapper mapper;
  private final Encoder encoder;

  public CreateAccount(AccountRepositoryGateway repository,
                       CreateAccountInputValidator validator,
                       AccountDTOMapper mapper, Encoder encoder)
  {
    this.repository = repository;
    this.validator = validator;
    this.mapper = mapper;
    this.encoder = encoder;
  }

  @Override
  public AccountDTOResponse execute(AccountDTORequest dto) throws ValidationErrorException
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
