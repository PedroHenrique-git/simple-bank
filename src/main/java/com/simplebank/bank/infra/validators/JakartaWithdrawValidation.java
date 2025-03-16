package com.simplebank.bank.infra.validators;

import com.simplebank.bank.usecases.ports.InputValidator;
import com.simplebank.bank.usecases.ports.WithdrawDTORequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class JakartaWithdrawValidation extends AbstractValidator
    implements InputValidator<WithdrawDTORequest>
{
  @Override
  public List<String> validate(WithdrawDTORequest dto)
  {
    var schema = new JakartaWithdrawValidation.Schema(dto.accountId(), dto.amount());
    Set<ConstraintViolation<JakartaWithdrawValidation.Schema>> violations =
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
