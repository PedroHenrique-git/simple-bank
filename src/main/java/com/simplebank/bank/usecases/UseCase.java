package com.simplebank.bank.usecases;

import com.simplebank.bank.domain.exceptions.ValidationErrorException;

public interface UseCase<T, R>
{
  R execute(T data) throws ValidationErrorException;
}
