package com.simplebank.bank.infra.validators;

import com.simplebank.bank.domain.DomainRegexMap;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.InputValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

public class JakartaLoginValidation extends AbstractValidator
    implements InputValidator<AuthLoginDTORequest>
{
  @Override
  public List<String> validate(AuthLoginDTORequest dto)
  {
    var schema = new JakartaLoginValidation.Schema(dto.email(), dto.password());
    Set<ConstraintViolation<JakartaLoginValidation.Schema>> violations =
        validator.validate(schema);

    return violations.stream().map(ConstraintViolation::getMessage).toList();
  }

  private record Schema(
      @Email(regexp = DomainRegexMap.EMAIL,
          flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email")
      @NotNull(message = "The email must not be null")
      String email,

      @NotNull(message = "The password must not be null")
      String password
  )
  {
  }
}
