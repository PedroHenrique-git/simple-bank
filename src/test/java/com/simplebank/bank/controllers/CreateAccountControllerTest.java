package com.simplebank.bank.controllers;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(value = {UserConfig.class, AccountConfig.class, TransactionConfig.class,
    CommonConfig.class})
public class CreateAccountControllerTest
{
  @Autowired
  public WebController<CreateAccountDTOResponse, CreateAccountDTORequest> controller;

  @Test
  void testCreateAccountController()
  {
    var responseWithError = controller.handle(new HttpRequest<>(
        new CreateAccountDTORequest("pedro", "p@email.com", "1234", "111.111.111-11")));

    assertEquals(400, responseWithError.status());
    assertEquals("Invalid User input", responseWithError.message());
    assertNull(responseWithError.body());
    assertFalse(responseWithError.errors().isEmpty());
    assertFalse(responseWithError.success());

    var responseWithSuccess = controller.handle(new HttpRequest<>(
        new CreateAccountDTORequest("pedro", "p@email.com", "AA!45aaa", "111.111.111-11")));

    assertEquals(201, responseWithSuccess.status());
    assertEquals("account created successfully", responseWithSuccess.message());
    assertInstanceOf(CreateAccountDTOResponse.class, responseWithSuccess.body());
    assertTrue(responseWithSuccess.errors().isEmpty());
    assertTrue(responseWithSuccess.success());
  }
}
