package com.simplebank.bank.usecases.ports;

public record CreateAccountDTOResponse(Long accountId, Long userId, String name, String email)
{
}
