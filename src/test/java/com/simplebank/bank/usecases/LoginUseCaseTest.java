package com.simplebank.bank.usecases;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(value = {UserConfig.class, AccountConfig.class, TransactionConfig.class,
    CommonConfig.class})
public class LoginUseCaseTest
{
  @Autowired
  public UseCase<AuthLoginDTORequest, AuthLoginDTOResponse> loginUsecase;

  @Autowired
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUsecase;

  @Test
  void testLoginUseCase() throws ForbiddenException, UseCaseException
  {
    var ac = createAccountUsecase.execute(
        new CreateAccountDTORequest("Pedro", "p50@email.com", "AA!45aaa", "329.959.880-60"));

    assertThrows(UseCaseException.class,
        () -> loginUsecase.execute(new AuthLoginDTORequest(null, null)));
    assertThrows(UseCaseException.class,
        () -> loginUsecase.execute(new AuthLoginDTORequest("p50@email.comABC", "1214")));
    assertThrows(UseCaseException.class,
        () -> loginUsecase.execute(new AuthLoginDTORequest("p50@email.com", "1214")));

    var loginResult = loginUsecase.execute(new AuthLoginDTORequest("p50@email.com", "AA!45aaa"));

    assertInstanceOf(AuthLoginDTOResponse.class, loginResult);
    assertInstanceOf(String.class, loginResult.commonToken());
    assertInstanceOf(String.class, loginResult.refreshToken());
  }
}
