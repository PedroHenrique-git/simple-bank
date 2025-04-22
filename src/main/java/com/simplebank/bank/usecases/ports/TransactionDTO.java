package com.simplebank.bank.usecases.ports;

public record TransactionDTO(Long id, Double amount, String payer, String payee)
{
}
