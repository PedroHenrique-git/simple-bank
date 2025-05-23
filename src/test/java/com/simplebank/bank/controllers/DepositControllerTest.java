package com.simplebank.bank.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
@SpringBootTest
public class DepositControllerTest
{
  @Autowired
  public WebController<CreateAccountDTOResponse, CreateAccountDTORequest> accountController;

  @Autowired
  public WebController<DepositDTOResponse, DepositDTORequest> controller;

  @MockitoBean
  AuthManager authManager;

  @BeforeEach
  void setup() throws ForbiddenException
  {
    when(authManager.isAuthorized(anyLong())).thenReturn(true);
  }

  @Test
  void testDepositController() throws ForbiddenException
  {
    var account = accountController.handle(new HttpRequest<>(
        new CreateAccountDTORequest("pedro", "p2@email.com", "AA!45aaa", "222.222.222-22")));

    var responseWithError =
        controller.handle(
            new HttpRequest<>(new DepositDTORequest(account.body().accountId(), -100.0)));

    assertEquals(400, responseWithError.status());
    assertEquals("Invalid deposit input", responseWithError.message());
    assertNull(responseWithError.body());
    assertFalse(responseWithError.errors().isEmpty());
    assertFalse(responseWithError.success());

    when(authManager.isAuthorized(anyLong())).thenThrow(new ForbiddenException());

    var responseWithForbiddenError =
        controller.handle(
            new HttpRequest<>(new DepositDTORequest(account.body().accountId(), 100.0)));

    assertEquals(403, responseWithForbiddenError.status());
    assertEquals("You can not perform this operation",
        responseWithForbiddenError.message());
    assertNull(responseWithForbiddenError.body());
    assertTrue(responseWithForbiddenError.errors().isEmpty());
    assertFalse(responseWithForbiddenError.success());

    Mockito.reset(authManager);

    var responseWithSuccess =
        controller.handle(
            new HttpRequest<>(new DepositDTORequest(account.body().accountId(), 100.0)));

    assertEquals(200, responseWithSuccess.status());
    assertEquals("deposit made successfully", responseWithSuccess.message());
    assertInstanceOf(DepositDTOResponse.class, responseWithSuccess.body());
    assertTrue(responseWithSuccess.errors().isEmpty());
    assertTrue(responseWithSuccess.success());
  }
}
