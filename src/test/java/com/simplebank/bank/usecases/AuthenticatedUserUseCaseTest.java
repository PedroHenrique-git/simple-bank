package com.simplebank.bank.usecases;

import com.simplebank.bank.config.AccountConfig;
import com.simplebank.bank.config.CommonConfig;
import com.simplebank.bank.config.TransactionConfig;
import com.simplebank.bank.config.UserConfig;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.mocks.UserMock;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTORequest;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
public class AuthenticatedUserUseCaseTest
{
  @Autowired
  public UseCase<AuthAuthenticatedUserDTORequest, AuthAuthenticatedUserDTOResponse> usecase;

  @MockitoBean
  AuthManager authManager;

  @Test
  void testAuthenticatedUser() throws ForbiddenException, UseCaseException
  {
    when(authManager.getAuthenticatedUser()).thenReturn(UserMock.createClientUser());

    var authenticatedUser = usecase.execute(new AuthAuthenticatedUserDTORequest());

    assertEquals("test-user@email.com", authenticatedUser.email());
    assertEquals("test", authenticatedUser.name());
  }
}
