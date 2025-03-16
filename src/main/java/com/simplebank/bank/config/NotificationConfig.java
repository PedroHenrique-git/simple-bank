package com.simplebank.bank.config;

import com.simplebank.bank.infra.notification.RabbitMqNotificationReceiver;
import com.simplebank.bank.infra.notification.RabbitMqNotificationSender;
import com.simplebank.bank.services.EmailService;
import com.simplebank.bank.services.EmailServiceInMailSender;
import com.simplebank.bank.usecases.ports.TransferNotificationSender;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
public class NotificationConfig
{
  @Bean
  public Queue notificationQueue(@Value("${notification.queue.name}") String notificationQueueName)
  {
    return new Queue(notificationQueueName);
  }

  @Bean
  public RabbitMqNotificationReceiver receiver(EmailService emailService)
  {
    return new RabbitMqNotificationReceiver(emailService);
  }

  @Bean
  public TransferNotificationSender sender(Queue queue, RabbitTemplate rabbitTemplate)
  {
    return new RabbitMqNotificationSender(rabbitTemplate, queue);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
  {
    return new RabbitTemplate(connectionFactory);
  }

  @Bean
  public EmailService emailService(MailSender sender)
  {
    return new EmailServiceInMailSender(sender);
  }
}
