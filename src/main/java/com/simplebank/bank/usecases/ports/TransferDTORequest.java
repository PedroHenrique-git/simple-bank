package com.simplebank.bank.usecases.ports;

public record TransferDTORequest(Double amount, Long payerId, Long payeeId)
{
}
