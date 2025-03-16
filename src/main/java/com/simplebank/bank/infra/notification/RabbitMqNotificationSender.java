package com.simplebank.bank.infra.notification;

import com.simplebank.bank.infra.serialization.SerializationUtils;
import com.simplebank.bank.usecases.ports.TransferNotificationPayload;
import com.simplebank.bank.usecases.ports.TransferNotificationSender;
import java.util.Objects;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMqNotificationSender implements TransferNotificationSender
{
  private final RabbitTemplate template;
  private final Queue queue;

  public RabbitMqNotificationSender(RabbitTemplate template, Queue queue)
  {
    this.template = template;
    this.queue = queue;
  }

  @Override
  public void send(TransferNotificationPayload payload)
  {
    this.template.convertAndSend(queue.getName(),
        Objects.requireNonNull(SerializationUtils.toJson(payload)));
  }
}
