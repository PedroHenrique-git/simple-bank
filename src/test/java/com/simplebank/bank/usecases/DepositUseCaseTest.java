package com.simplebank.bank.usecases;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@ActiveProfiles("test")
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

  @BeforeEach
  void setup() throws ForbiddenException
  {
    when(authManager.isAuthorized(anyLong())).thenReturn(true);
  }

  @Test
  void testDepositUseCase() throws UseCaseException, ForbiddenException
  {
    var account = createAccountUseCase.execute(
        new CreateAccountDTORequest("Pedro", "p3@email.com", "AA!45aaa", "444.444.444-44"));

    assertThrows(UseCaseException.class,
        () -> usecase.execute(new DepositDTORequest(-1L, 0.0)));
    assertThrows(UseCaseException.class,
        () -> usecase.execute(new DepositDTORequest(account.accountId(), 0.0)));

    var updatedAccount = usecase.execute(new DepositDTORequest(account.accountId(), 100.0));

    assertEquals(100, updatedAccount.balance());
  }
}
