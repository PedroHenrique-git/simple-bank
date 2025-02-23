package com.simplebank.bank.usecases.ports;

import java.util.List;

public interface InputValidator<T>
{
  List<String> validate(T dto);
}
