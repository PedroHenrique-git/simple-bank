package com.simplebank.bank.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.services.TransferAuthService;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;
import com.simplebank.bank.usecases.ports.TransferNotificationDTO;
import com.simplebank.bank.usecases.ports.TransferNotificationSender;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransferController
{
  @Autowired
  public WebController<TransferDTOResponse, TransferDTORequest>
      controller;

  @Autowired
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUsecase;

  @Autowired
  public UseCase<DepositDTORequest, DepositDTOResponse> depositUsecase;

  @MockitoBean
  AuthManager authManager;

  @MockitoBean
  TransferAuthService transferAuthService;

  @MockitoBean
  TransferNotificationSender transferNotificationSender;

  @Test
  void testTransferController() throws ForbiddenException, UseCaseException, UnauthorizedException
  {
    var acOne = createAccountUsecase.execute(
        new CreateAccountDTORequest("pedro", "p@email.com", "AA!45aaa", "610.425.140-78"));
    var acTwo = createAccountUsecase.execute(
        new CreateAccountDTORequest("pedro", "p1@email.com", "AA!45aaa", "84.919.051/0001-90"));

    var responseWithError =
        controller.handle(new HttpRequest<>(new TransferDTORequest(0.0, 0L, 0L)));

    assertEquals(400, responseWithError.status());
    assertEquals("Invalid transfer input", responseWithError.message());
    assertNull(responseWithError.body());
    assertFalse(responseWithError.errors().isEmpty());
    assertFalse(responseWithError.success());

    var responseWithPayerError =
        controller.handle(
            new HttpRequest<>(new TransferDTORequest(100.0, acTwo.accountId(), acOne.accountId())));

    assertEquals(400, responseWithPayerError.status());
    assertEquals("A business user can't make transfers", responseWithPayerError.message());
    assertNull(responseWithPayerError.body());
    assertTrue(responseWithPayerError.errors().isEmpty());
    assertFalse(responseWithPayerError.success());

    when(transferAuthService.authorize()).thenReturn(false);

    var responseWithAuthorizationError =
        controller.handle(
            new HttpRequest<>(new TransferDTORequest(100.0, acOne.accountId(), acTwo.accountId())));

    assertEquals(400, responseWithPayerError.status());
    assertEquals("The transfer was not authorized", responseWithAuthorizationError.message());
    assertNull(responseWithAuthorizationError.body());
    assertTrue(responseWithAuthorizationError.errors().isEmpty());
    assertFalse(responseWithAuthorizationError.success());

    when(transferAuthService.authorize()).thenReturn(true);

    var responseWithAmountError =
        controller.handle(
            new HttpRequest<>(new TransferDTORequest(100.0, acOne.accountId(), acTwo.accountId())));

    assertEquals(400, responseWithPayerError.status());
    assertEquals("There is no balance available to carry out the operation",
        responseWithAmountError.message());
    assertNull(responseWithAmountError.body());
    assertTrue(responseWithAmountError.errors().isEmpty());
    assertFalse(responseWithAmountError.success());

    when(authManager.isAuthorized(anyLong())).thenThrow(new ForbiddenException());

    var responseWithForbiddenError =
        controller.handle(
            new HttpRequest<>(new TransferDTORequest(100.0, acOne.accountId(), acTwo.accountId())));

    assertEquals(403, responseWithForbiddenError.status());
    assertEquals("You can not perform this operation",
        responseWithForbiddenError.message());
    assertNull(responseWithForbiddenError.body());
    assertTrue(responseWithForbiddenError.errors().isEmpty());
    assertFalse(responseWithForbiddenError.success());

    Mockito.reset(authManager);

    when(authManager.isAuthorized(anyLong())).thenReturn(true);
    doNothing().when(transferNotificationSender).send(any(TransferNotificationDTO.class));

    depositUsecase.execute(new DepositDTORequest(acOne.accountId(), 1000.0));

    var successResponse =
        controller.handle(
            new HttpRequest<>(new TransferDTORequest(100.0, acOne.accountId(), acTwo.accountId())));

    assertEquals(200, successResponse.status());
    assertEquals("transfer made successfully",
        successResponse.message());
    assertInstanceOf(TransferDTOResponse.class, successResponse.body());
    assertTrue(successResponse.errors().isEmpty());
    assertTrue(successResponse.success());
  }
}
