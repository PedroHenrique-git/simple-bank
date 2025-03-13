package com.simplebank.bank.usecases.ports;

public record TransferDTORequest(Double value, Long payerId, Long payeeId)
{
}
