package com.simplebank.bank.usecases;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.mocks.UserMock;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTORequest;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTOResponse;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import org.junit.jupiter.api.Test;
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
public class AuthenticatedUserAccountUseCaseTest
{
  @Autowired
  UseCase<AuthenticatedUserAccountDTORequest, AuthenticatedUserAccountDTOResponse> usecase;

  @Autowired
  UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUsecase;

  @MockitoBean
  AuthManager authManager;

  @Test
  void testAuthenticatedAccountUser()
      throws ForbiddenException, UnauthorizedException, UseCaseException
  {
    var ac = createAccountUsecase.execute(
        new CreateAccountDTORequest("Pedro", "p50@email.com", "AA!45aaa", "329.959.880-60"));
    var user = UserMock.createClientUser();

    user.setId(ac.userId());
    user.setEmail(ac.email());
    user.setName(ac.name());

    when(authManager.getAuthenticatedUser()).thenReturn(user);

    var authenticatedAccount = usecase.execute(new AuthenticatedUserAccountDTORequest());

    assertInstanceOf(AuthenticatedUserAccountDTOResponse.class, authenticatedAccount);
  }
}
