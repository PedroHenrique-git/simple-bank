package com.simplebank.bank.usecases;

import com.simplebank.bank.data.gateways.AccountRepositoryGateway;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.mapper.CreateAccountDTOMapper;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.Encoder;
import com.simplebank.bank.usecases.ports.InputValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
  @Transactional
  public CreateAccountDTOResponse execute(CreateAccountDTORequest dto)
      throws UseCaseException
  {
    var violations = validator.validate(dto);

    if (!violations.isEmpty())
    {
      throw new UseCaseException("Invalid User input", violations);
    }

    var model = mapper.toAccount(dto);

    model.getUser().setPassword(encoder.encode(dto.password()));

    var createdAccount = repository.save(model);

    return mapper.toResponse(createdAccount);
  }
}
