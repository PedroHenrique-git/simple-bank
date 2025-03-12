package com.simplebank.bank.controllers;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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
public class DepositControllerTest
{
  @Autowired
  public WebController<CreateAccountDTOResponse, CreateAccountDTORequest> accountController;

  @Autowired
  public WebController<DepositDTOResponse, DepositDTORequest> controller;

  @Test
  void testDepositController()
  {
    var account = accountController.handle(new HttpRequest<>(
        new CreateAccountDTORequest("pedro", "p@email.com", "AA!45aaa", "111.111.111-11")));

    var responseWithError =
        controller.handle(new HttpRequest<>(new DepositDTORequest(account.body().id(), -100.0)));

    assertEquals(400, responseWithError.status());
    assertEquals("Invalid deposit input", responseWithError.message());
    assertInstanceOf(List.class, responseWithError.body());
    assertFalse(responseWithError.success());

    var responseWithSuccess =
        controller.handle(new HttpRequest<>(new DepositDTORequest(account.body().id(), 100.0)));

    assertEquals(200, responseWithSuccess.status());
    assertEquals("deposit made successfully", responseWithSuccess.message());
    assertInstanceOf(DepositDTOResponse.class, responseWithSuccess.body());
    assertTrue(responseWithSuccess.success());
  }
}
