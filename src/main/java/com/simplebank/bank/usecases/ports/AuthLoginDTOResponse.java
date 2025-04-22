package com.simplebank.bank.usecases.ports;

public record AuthLoginDTOResponse(String authToken, String refreshToken,
                                   AuthAuthenticatedUserDTOResponse user)
{
}
