package com.simplebank.bank;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class BankApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(BankApplication.class, args);
  }

}
