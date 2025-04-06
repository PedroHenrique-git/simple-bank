package com.simplebank.bank.controllers;

import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthLogoutDTORequest;
import com.simplebank.bank.usecases.ports.AuthLogoutDTOResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class LogoutController
{
  @Autowired
  public WebController<AuthLogoutDTOResponse, AuthLogoutDTORequest>
      controller;

  @Test
  void testLogoutController()
  {
    var response = controller.handle(new HttpRequest<>(new AuthLogoutDTORequest()));

    assertEquals("/login", response.body().redirectUrl());
  }
}
