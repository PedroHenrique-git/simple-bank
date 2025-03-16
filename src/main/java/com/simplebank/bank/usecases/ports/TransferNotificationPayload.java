package com.simplebank.bank.usecases.ports;

public record TransferNotificationPayload(String payerEmail, String payeeEmail, double value)
{
}
