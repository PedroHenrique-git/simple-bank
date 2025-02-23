package com.simplebank.bank.infra.validators;

import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.InputValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class JakartaDepositValidation extends AbstractValidator
    implements InputValidator<DepositDTORequest>
{
  @Override
  public List<String> validate(DepositDTORequest dto)
  {
    var schema = new JakartaDepositValidation.Schema(dto.accountId(), dto.amount());
    Set<ConstraintViolation<JakartaDepositValidation.Schema>> violations =
        validator.validate(schema);

    return violations.stream().map(ConstraintViolation::getMessage).toList();
  }

  private record Schema(
      @NotNull(message = "The accountId must not be null")
      Long accountId,

      @NotNull(message = "The amount must not be null")
      @Min(value = 1, message = "The amount must be greater than zero")
      Double amount
  )
  {
  }
}
