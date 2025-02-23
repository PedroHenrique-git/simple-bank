package com.simplebank.bank.usecases.ports;

import java.util.List;

public interface CreateAccountInputValidator
{
  List<String> validate(AccountDTORequest dto);
}
