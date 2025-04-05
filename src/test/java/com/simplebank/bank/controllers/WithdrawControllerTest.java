package com.simplebank.bank.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import com.simplebank.bank.usecases.ports.WithdrawDTORequest;
import com.simplebank.bank.usecases.ports.WithdrawDTOResponse;
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
public class WithdrawControllerTest
{
  @Autowired
  public WebController<CreateAccountDTOResponse, CreateAccountDTORequest> accountController;

  @Autowired
  public WebController<WithdrawDTOResponse, WithdrawDTORequest> controller;

  @Autowired
  public UseCase<DepositDTORequest, DepositDTOResponse> depositUsecase;

  @MockitoBean
  AuthManager authManager;

  @BeforeEach
  void setup() throws ForbiddenException
  {
    when(authManager.isAuthorized(anyLong())).thenReturn(true);
  }

  @Test
  void testWithdrawController() throws ForbiddenException, UseCaseException
  {
    var account = accountController.handle(new HttpRequest<>(
        new CreateAccountDTORequest("pedro", "p2@email.com", "AA!45aaa", "222.222.222-22")));

    var responseWithError =
        controller.handle(
            new HttpRequest<>(new WithdrawDTORequest(account.body().accountId(), -100.0)));

    assertEquals(400, responseWithError.status());
    assertEquals("Invalid withdraw input", responseWithError.message());
    assertNull(responseWithError.body());
    assertFalse(responseWithError.errors().isEmpty());
    assertFalse(responseWithError.success());

    when(authManager.isAuthorized(anyLong())).thenThrow(new ForbiddenException());

    var responseWithForbiddenError =
        controller.handle(
            new HttpRequest<>(new WithdrawDTORequest(account.body().accountId(), 100.0)));

    assertEquals(403, responseWithForbiddenError.status());
    assertEquals("You can not perform this operation",
        responseWithForbiddenError.message());
    assertNull(responseWithForbiddenError.body());
    assertTrue(responseWithForbiddenError.errors().isEmpty());
    assertFalse(responseWithForbiddenError.success());

    Mockito.reset(authManager);

    var responseWithAmountError =
        controller.handle(
            new HttpRequest<>(new WithdrawDTORequest(account.body().accountId(), 100.0)));

    assertEquals(400, responseWithAmountError.status());
    assertEquals("There is no balance available to carry out the operation",
        responseWithAmountError.message());
    assertNull(responseWithAmountError.body());
    assertTrue(responseWithAmountError.errors().isEmpty());
    assertFalse(responseWithAmountError.success());

    depositUsecase.execute(new DepositDTORequest(account.body().accountId(), 1000.0));

    var responseWithSuccess =
        controller.handle(
            new HttpRequest<>(new WithdrawDTORequest(account.body().accountId(), 100.0)));

    assertEquals(200, responseWithSuccess.status());
    assertEquals("withdraw made successfully", responseWithSuccess.message());
    assertInstanceOf(WithdrawDTOResponse.class, responseWithSuccess.body());
    assertTrue(responseWithSuccess.errors().isEmpty());
    assertTrue(responseWithSuccess.success());
  }
}
