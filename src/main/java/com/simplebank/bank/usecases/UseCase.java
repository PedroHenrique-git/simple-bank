package com.simplebank.bank.usecases;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;

public interface UseCase<T, R>
{
  R execute(T data) throws UseCaseException, ForbiddenException;
}
