package com.simplebank.bank.infra.notification;

import com.simplebank.bank.infra.currency.FormatCurrency;
import com.simplebank.bank.infra.serialization.SerializationUtils;
import com.simplebank.bank.services.EmailService;
import com.simplebank.bank.usecases.ports.TransferNotificationPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMqNotificationReceiver
{
  private final EmailService emailService;

  public RabbitMqNotificationReceiver(EmailService emailService)
  {
    this.emailService = emailService;
  }

  @RabbitListener(queues = "${notification.queue.name}")
  public void receive(String payload)
  {
    var p = deserializePayload(payload);

    var payerEmail = p.payerEmail();
    var payeeEmail = p.payeeEmail();
    var transferValue = p.value();

    emailService.send(payerEmail,
        String.format("Your transfer to %s has been made, in the amount of %s", payeeEmail,
            FormatCurrency.format(transferValue)));

    emailService.send(payeeEmail,
        String.format("You received a transfer from %s, in the amount of %s", payerEmail,
            FormatCurrency.format(transferValue)));
  }

  private TransferNotificationPayload deserializePayload(String payload)
  {
    return SerializationUtils.fromJson(payload, TransferNotificationPayload.class);
  }
}
