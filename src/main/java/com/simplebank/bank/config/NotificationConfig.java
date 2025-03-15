package com.simplebank.bank.config;

import com.simplebank.bank.infra.notification.NotificationReceiver;
import com.simplebank.bank.infra.notification.NotificationSender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig
{
  @Bean
  public Queue notificationQueue(@Value("${notification.queue.name}") String notificationQueueName)
  {
    return new Queue(notificationQueueName);
  }

  @Bean
  public NotificationReceiver receiver()
  {
    return new NotificationReceiver();
  }

  @Bean
  public NotificationSender sender(Queue queue, RabbitTemplate rabbitTemplate)
  {
    return new NotificationSender(rabbitTemplate, queue);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
  {
    return new RabbitTemplate(connectionFactory);
  }
}
