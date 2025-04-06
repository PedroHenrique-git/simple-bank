package com.simplebank.bank.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
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
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class LoginController
{
  @Autowired
  public WebController<AuthLoginDTOResponse, AuthLoginDTORequest>
      controller;

  @Autowired
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUsecase;

  @Test
  void testLoginController() throws ForbiddenException, UseCaseException, UnauthorizedException
  {
    var responseWithError =
        controller.handle(new HttpRequest<>(new AuthLoginDTORequest("mock", "1")));

    assertEquals(400, responseWithError.status());
    assertEquals("Invalid login input", responseWithError.message());
    assertNull(responseWithError.body());
    assertFalse(responseWithError.errors().isEmpty());
    assertFalse(responseWithError.success());

    createAccountUsecase.execute(
        new CreateAccountDTORequest("Pedro", "p600@email.com", "AA!45aaa", "329.959.880-99"));

    var responseWithIncorrectCredentials =
        controller.handle(new HttpRequest<>(new AuthLoginDTORequest("p50@email.com", "AA")));

    assertEquals(400, responseWithIncorrectCredentials.status());
    assertEquals("Invalid email or password", responseWithIncorrectCredentials.message());
    assertNull(responseWithIncorrectCredentials.body());
    assertTrue(responseWithIncorrectCredentials.errors().isEmpty());
    assertFalse(responseWithIncorrectCredentials.success());

    var successResponse =
        controller.handle(new HttpRequest<>(new AuthLoginDTORequest("p600@email.com", "AA!45aaa")));

    assertEquals(200, successResponse.status());
    assertEquals("login successful", successResponse.message());
    assertInstanceOf(AuthLoginDTOResponse.class, successResponse.body());
    assertTrue(successResponse.errors().isEmpty());
    assertTrue(successResponse.success());
  }
}
