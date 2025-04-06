package com.simplebank.bank.usecases;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.services.TransferAuthService;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;
import com.simplebank.bank.usecases.ports.TransferNotificationDTO;
import com.simplebank.bank.usecases.ports.TransferNotificationSender;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(value = {UserConfig.class, AccountConfig.class, TransactionConfig.class,
    CommonConfig.class})
public class TransferUseCaseTest
{
  @Autowired
  public UseCase<DepositDTORequest, DepositDTOResponse> depositUsecase;

  @Autowired
  public UseCase<TransferDTORequest, TransferDTOResponse> transferUsecase;

  @Autowired
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUsecase;

  @MockitoBean
  AuthManager authManager;

  @MockitoBean
  TransferAuthService authService;

  @MockitoBean
  TransferNotificationSender transferNotificationSender;

  @Test
  void testTransferUseCase() throws ForbiddenException, UnauthorizedException, UseCaseException
  {
    when(authManager.isAuthorized(anyLong())).thenReturn(true);
    when(authService.authorize()).thenReturn(true);
    doNothing().when(transferNotificationSender).send(any(TransferNotificationDTO.class));

    var acOne = createAccountUsecase.execute(
        new CreateAccountDTORequest("pedro", "p@email.com", "AA!45aaa", "610.425.140-46"));

    var acTwo = createAccountUsecase.execute(
        new CreateAccountDTORequest("pedro", "p1@email.com", "AA!45aaa", "84.919.051/0001-01"));

    depositUsecase.execute(new DepositDTORequest(acOne.accountId(), 1000.0));
    depositUsecase.execute(new DepositDTORequest(acTwo.accountId(), 1000.0));

    assertThrows(UseCaseException.class, () -> transferUsecase.execute(
        new TransferDTORequest(100.0, -1L, acOne.accountId())));
    assertThrows(UseCaseException.class, () -> transferUsecase.execute(
        new TransferDTORequest(100.0, acTwo.accountId(), -1L)));
    assertThrows(UseCaseException.class, () -> transferUsecase.execute(
        new TransferDTORequest(100.0, acTwo.accountId(), acOne.accountId())));
    assertThrows(UseCaseException.class, () -> transferUsecase.execute(
        new TransferDTORequest(-100.0, acOne.accountId(), acTwo.accountId())));

    var transferResult = transferUsecase.execute(
        new TransferDTORequest(100.0, acOne.accountId(), acTwo.accountId()));

    assertEquals(900.0, transferResult.value());
  }

  @Test
  void testTransferAuthorization()
      throws ForbiddenException, UnauthorizedException, UseCaseException
  {
    when(authManager.isAuthorized(anyLong())).thenReturn(false);
    when(authService.authorize()).thenReturn(false);
    doNothing().when(transferNotificationSender).send(any(TransferNotificationDTO.class));

    var acOne = createAccountUsecase.execute(
        new CreateAccountDTORequest("pedro", "p@email.com", "AA!45aaa", "610.425.140-46"));

    var acTwo = createAccountUsecase.execute(
        new CreateAccountDTORequest("pedro", "p1@email.com", "AA!45aaa", "84.919.051/0001-01"));

    depositUsecase.execute(new DepositDTORequest(acOne.accountId(), 1000.0));
    depositUsecase.execute(new DepositDTORequest(acTwo.accountId(), 1000.0));

    assertThrows(UseCaseException.class, () -> transferUsecase.execute(
        new TransferDTORequest(100.0, acOne.accountId(), acTwo.accountId())));

    when(authManager.isAuthorized(anyLong())).thenReturn(true);
    when(authService.authorize()).thenReturn(true);

    assertDoesNotThrow(() -> transferUsecase.execute(
        new TransferDTORequest(100.0, acOne.accountId(), acTwo.accountId())));
  }
}
