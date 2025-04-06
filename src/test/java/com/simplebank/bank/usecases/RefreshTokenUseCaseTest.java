package com.simplebank.bank.usecases;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import com.simplebank.bank.usecases.ports.RefreshAuthDTORequest;
import com.simplebank.bank.usecases.ports.RefreshAuthDTOResponse;
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
public class RefreshTokenUseCaseTest
{
  @Autowired
  public UseCase<RefreshAuthDTORequest, RefreshAuthDTOResponse> usecase;

  @Autowired
  public UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUseCase;

  @Autowired
  public UseCase<AuthLoginDTORequest, AuthLoginDTOResponse> loginUseCase;

  @Test
  void testRefreshTokenUseCase() throws ForbiddenException, UnauthorizedException, UseCaseException
  {
    createAccountUseCase.execute(
        new CreateAccountDTORequest("pedro", "p800@gmail.com", "AA!45aaa", "406.260.930-40"));

    var login = loginUseCase.execute(new AuthLoginDTORequest("p800@gmail.com", "AA!45aaa"));

    assertThrows(UnauthorizedException.class, () -> usecase.execute(new RefreshAuthDTORequest("")));
    assertThrows(UseCaseException.class,
        () -> usecase.execute(new RefreshAuthDTORequest(login.authToken())));

    var response = usecase.execute(new RefreshAuthDTORequest(login.refreshToken()));

    assertInstanceOf(String.class, response.authToken());
  }
}
