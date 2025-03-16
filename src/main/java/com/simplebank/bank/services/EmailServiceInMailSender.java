package com.simplebank.bank.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailServiceInMailSender implements EmailService
{
  private final MailSender mailSender;

  @Value("${notification.mail.from}")
  private String from;

  public EmailServiceInMailSender(MailSender mailSender)
  {
    this.mailSender = mailSender;
  }

  @Override
  public void send(String to, String message)
  {
    var mailMessage = new SimpleMailMessage();

    mailMessage.setFrom(from);
    mailMessage.setTo(to);
    mailMessage.setText(message);

    this.mailSender.send(mailMessage);
  }
}
