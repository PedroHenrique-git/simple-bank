package com.simplebank.bank.infra.validators;


import jakarta.validation.Validation;
import jakarta.validation.Validator;

public abstract class AbstractValidator
{
  protected final Validator validator;

  protected AbstractValidator()
  {
    this.validator = Validation.buildDefaultValidatorFactory().getValidator();
  }
}
