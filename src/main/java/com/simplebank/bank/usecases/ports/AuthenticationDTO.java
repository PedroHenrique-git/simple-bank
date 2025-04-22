package com.simplebank.bank.usecases.ports;

public record AuthenticationDTO(String commonToken, String refreshToken,
                                AuthAuthenticatedUserDTOResponse user)
{
}
