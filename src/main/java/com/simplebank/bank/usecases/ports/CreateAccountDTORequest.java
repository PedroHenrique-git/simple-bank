package com.simplebank.bank.usecases.ports;

public record CreateAccountDTORequest(String name, String email, String password, String document)
{
}
