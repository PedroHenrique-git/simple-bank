package com.simplebank.bank.controllers;

import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.RefreshAuthDTORequest;
import com.simplebank.bank.usecases.ports.RefreshAuthDTOResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class RefreshTokenController
{
  @Autowired
  public WebController<RefreshAuthDTOResponse, RefreshAuthDTORequest> controller;

  @Autowired
  public WebController<CreateAccountDTOResponse, CreateAccountDTORequest> createAccountController;

  @Autowired
  public WebController<AuthLoginDTOResponse, AuthLoginDTORequest> loginController;

  @Test
  void testRefreshTokenController()
  {
    createAccountController.handle(
        new HttpRequest<>(
            new CreateAccountDTORequest("pedro", "p800@gmail.com", "AA!45aaa", "406.260.930-40")));

    var login = loginController.handle(
        new HttpRequest<>(new AuthLoginDTORequest("p800@gmail.com", "AA!45aaa")));

    var responseWithUnauthorizedError =
        controller.handle(new HttpRequest<>(new RefreshAuthDTORequest("")));

    assertEquals(401, responseWithUnauthorizedError.status());
    assertEquals("Unauthorized", responseWithUnauthorizedError.message());
    assertNull(responseWithUnauthorizedError.body());
    assertTrue(responseWithUnauthorizedError.errors().isEmpty());
    assertFalse(responseWithUnauthorizedError.success());

    var responseWithForbiddenError =
        controller.handle(new HttpRequest<>(new RefreshAuthDTORequest(login.body().authToken())));

    assertEquals(400, responseWithForbiddenError.status());
    assertEquals("Invalid refresh token", responseWithForbiddenError.message());
    assertNull(responseWithForbiddenError.body());
    assertTrue(responseWithForbiddenError.errors().isEmpty());
    assertFalse(responseWithForbiddenError.success());

    var successResponse =
        controller.handle(
            new HttpRequest<>(new RefreshAuthDTORequest(login.body().refreshToken())));

    assertEquals(200, successResponse.status());
    assertEquals("Token updated successfully", successResponse.message());
    assertInstanceOf(RefreshAuthDTOResponse.class, successResponse.body());
    assertTrue(successResponse.errors().isEmpty());
    assertTrue(successResponse.success());
  }
}
