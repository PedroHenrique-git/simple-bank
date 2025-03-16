package com.simplebank.bank.usecases.ports;

public record TransferNotificationDTO(String payerEmail, String payeeEmail, double value)
{
}
