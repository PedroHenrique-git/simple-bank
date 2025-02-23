package com.simplebank.bank.usecases.ports;

public record DepositDTORequest(Long accountId, Double amount)
{
}
