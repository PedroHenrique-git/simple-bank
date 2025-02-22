package com.simplebank.bank.usecase;

import com.simplebank.bank.domain.exception.ValidationErrorException;

public interface UseCase<T, R>
{
  R execute(T data) throws ValidationErrorException;
}
