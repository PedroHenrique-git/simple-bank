package com.simplebank.bank.presentation.controllers.ports;

public record HttpRequest<T>(T body)
{
}
