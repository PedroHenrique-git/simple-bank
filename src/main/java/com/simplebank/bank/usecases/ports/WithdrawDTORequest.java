package com.simplebank.bank.usecases.ports;

public record WithdrawDTORequest(Long accountId, Double amount)
{
}
