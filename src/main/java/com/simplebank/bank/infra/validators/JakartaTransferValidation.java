package com.simplebank.bank.infra.validators;

import com.simplebank.bank.usecases.ports.InputValidator;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public class JakartaTransferValidation extends AbstractValidator
    implements InputValidator<TransferDTORequest>
{
  @Override
  public List<String> validate(TransferDTORequest dto)
  {
    var schema = new JakartaTransferValidation.Schema(dto.amount(), dto.payerId(), dto.payeeId());
    Set<ConstraintViolation<JakartaTransferValidation.Schema>> violations =
        validator.validate(schema);

    return violations.stream().map(ConstraintViolation::getMessage).toList();
  }

  private record Schema(
      @NotNull(message = "The amount must not be null")
      @Min(value = 1, message = "The amount must be greater than zero")
      Double amount,

      @NotNull(message = "The payerId must not be null")
      Long payerId,

      @NotNull(message = "The payerId must not be null")
      Long payeeId
  )
  {
  }
}
