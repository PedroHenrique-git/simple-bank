package com.simplebank.bank.infra.notification;

import com.simplebank.bank.domain.models.User.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class NotificationSender
{
  private final RabbitTemplate template;
  private final Queue queue;

  public NotificationSender(RabbitTemplate template, Queue queue)
  {
    this.template = template;
    this.queue = queue;
  }

  public void send(User user)
  {
    this.template.convertAndSend(queue.getName(), user.getEmail());
  }
}
