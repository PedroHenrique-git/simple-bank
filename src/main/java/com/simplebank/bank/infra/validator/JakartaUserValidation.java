package com.simplebank.bank.infra.validator;

import com.simplebank.bank.usecase.ports.CreateUserInputValidator;
import com.simplebank.bank.usecase.ports.UserDTORequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class JakartaUserValidation implements CreateUserInputValidator
{
  @Override
  public List<String> validate(UserDTORequest dto)
  {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    var schema = new Schema(dto.name(), dto.email(), dto.password(), dto.document());
    Set<ConstraintViolation<Schema>> violations = validator.validate(schema);

    return violations.stream().map(ConstraintViolation::getMessage).toList();
  }

  private record Schema(
      @NotBlank(message = "The user name must not be blank") @NotNull(message = "The user name must not be null")
      String name,

      @NotBlank(message = "the user email must not be blank")
      @NotNull(message = "the user email must not the null")
      @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
          flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid email")
      @Size(max = 255, message = "The email must have a maximum of 255 characters")
      String email,

      @NotBlank(message = "the user password must not be blank")
      @NotNull(message = "the user password must not be null")
      @Pattern(
          regexp = "^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}$",
          message = "Invalid password, your password must follow the rules: at least two capital letters, at least one uppercase letter, at least two digits, at least two lowercase letters and at least eight characters."
      )
      @Size(min = 8, max = 255, message = "The password must be between 8 and 255 characters")
      String password,

      @NotBlank(message = "the user document must not be blank")
      @NotNull(message = "the user document must not be null")
      @Pattern(
          regexp = "^([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})$",
          message = "Invalid document"
      )
      String document
  )
  {
  }
}
