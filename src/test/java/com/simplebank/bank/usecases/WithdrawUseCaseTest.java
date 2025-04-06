package com.simplebank.bank.usecases;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import com.simplebank.bank.usecases.ports.WithdrawDTORequest;
import com.simplebank.bank.usecases.ports.WithdrawDTOResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
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
public class WithdrawUseCaseTest
{
  @Autowired
  public UseCase<WithdrawDTORequest, WithdrawDTOResponse> withdrawUsecase;

  @Autowired
  public UseCase<DepositDTORequest, DepositDTOResponse> depositUsecase;

  @Autowired
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUseCase;

  @MockitoBean
  AuthManager authManager;

  @Test
  void testWithdrawUseCase() throws UseCaseException, UnauthorizedException, ForbiddenException
  {
    when(authManager.isAuthorized(anyLong())).thenReturn(true);

    var account = createAccountUseCase.execute(
        new CreateAccountDTORequest("Pedro", "p50@email.com", "AA!45aaa", "329.959.880-58"));

    assertThrows(UseCaseException.class,
        () -> withdrawUsecase.execute(new WithdrawDTORequest(-1L, 0.0)));
    assertThrows(UseCaseException.class,
        () -> withdrawUsecase.execute(new WithdrawDTORequest(account.accountId(), 0.0)));

    depositUsecase.execute(new DepositDTORequest(account.accountId(), 200.0));

    var updatedAccount =
        withdrawUsecase.execute(new WithdrawDTORequest(account.accountId(), 150.0));

    assertEquals(50.0, updatedAccount.balance());
  }

  @Test
  void testDepositAuthorization() throws ForbiddenException, UnauthorizedException, UseCaseException
  {
    when(authManager.isAuthorized(anyLong())).thenThrow(new ForbiddenException());

    var account = createAccountUseCase.execute(
        new CreateAccountDTORequest("Pedro", "p51@email.com", "AA!45aaa", "329.959.880-58"));

    assertThrows(ForbiddenException.class,
        () -> withdrawUsecase.execute(new WithdrawDTORequest(account.accountId(), 100.0)));
  }
}
