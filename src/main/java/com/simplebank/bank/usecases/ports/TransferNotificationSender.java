package com.simplebank.bank.usecases.ports;

public interface TransferNotificationSender
{
  void send(TransferNotificationPayload payload);
}
