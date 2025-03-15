package com.simplebank.bank.infra.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationReceiver
{
  @RabbitListener(queues = "${notification.queue.name}")
  public void receive(String email)
  {
    System.out.println(" Sent email to '" + email + "'");
  }
}
