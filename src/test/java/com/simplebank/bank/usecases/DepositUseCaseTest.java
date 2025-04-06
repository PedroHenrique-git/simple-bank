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
public class DepositUseCaseTest
{
  @Autowired
  public UseCase<DepositDTORequest, DepositDTOResponse> usecase;

  @Autowired
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUseCase;

  @MockitoBean
  AuthManager authManager;

  @Test
  void testDepositUseCase() throws UseCaseException, UnauthorizedException, ForbiddenException
  {
    when(authManager.isAuthorized(anyLong())).thenReturn(true);

    var account = createAccountUseCase.execute(
        new CreateAccountDTORequest("Pedro", "p3@email.com", "AA!45aaa", "444.444.444-44"));

    assertThrows(UseCaseException.class,
        () -> usecase.execute(new DepositDTORequest(-1L, 0.0)));
    assertThrows(UseCaseException.class,
        () -> usecase.execute(new DepositDTORequest(account.accountId(), 0.0)));

    var updatedAccount = usecase.execute(new DepositDTORequest(account.accountId(), 100.0));

    assertEquals(100, updatedAccount.balance());
  }

  @Test
  void testDepositAuthorization() throws ForbiddenException, UnauthorizedException, UseCaseException
  {
    when(authManager.isAuthorized(anyLong())).thenThrow(new ForbiddenException());

    var account = createAccountUseCase.execute(
        new CreateAccountDTORequest("Pedro", "p3@email.com", "AA!45aaa", "444.444.444-44"));

    assertThrows(ForbiddenException.class,
        () -> usecase.execute(new DepositDTORequest(account.accountId(), 100.0)));
  }
}
