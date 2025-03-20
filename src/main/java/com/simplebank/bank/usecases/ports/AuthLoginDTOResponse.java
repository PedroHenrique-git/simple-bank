package com.simplebank.bank.usecases.ports;

public record AuthLoginDTOResponse(String commonToken, String refreshToken)
{
}
