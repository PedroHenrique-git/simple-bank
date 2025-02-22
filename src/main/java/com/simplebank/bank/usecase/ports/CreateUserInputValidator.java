package com.simplebank.bank.usecase.ports;

import java.util.List;

public interface CreateUserInputValidator
{
  List<String> validate(UserDTORequest dto);
}
