package com.simplebank.bank.usecases.ports;

public record AccountDTORequest(String name, String email, String password, String document)
{
}
