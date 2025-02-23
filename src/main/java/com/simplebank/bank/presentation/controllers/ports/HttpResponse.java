package com.simplebank.bank.presentation.controllers.ports;

public record HttpResponse<T>(int status, boolean success, String message, T body)
{

}
