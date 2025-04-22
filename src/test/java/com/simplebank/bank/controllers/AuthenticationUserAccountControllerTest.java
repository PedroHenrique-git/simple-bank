package com.simplebank.bank.controllers;

import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.UnauthorizedException;
import com.simplebank.bank.domain.exceptions.UseCaseException;
import com.simplebank.bank.mocks.UserMock;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.UseCase;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTORequest;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTOResponse;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
@SpringBootTest
public class AuthenticationUserAccountControllerTest
{
  @Autowired
  public WebController<AuthenticatedUserAccountDTOResponse, AuthenticatedUserAccountDTORequest>
      controller;

  @Autowired
  UseCase<CreateAccountDTORequest, CreateAccountDTOResponse> createAccountUsecase;

  @MockitoBean
  AuthManager authManager;

  @Test
  void testAuthenticatedUserAccountController()
      throws ForbiddenException, UnauthorizedException, UseCaseException
  {
    var ac = createAccountUsecase.execute(
        new CreateAccountDTORequest("Pedro", "p50@email.com", "AA!45aaa", "329.959.880-60"));
    var user = UserMock.createClientUser();

    user.setId(ac.userId());
    user.setEmail(ac.email());
    user.setName(ac.name());

    when(authManager.getAuthenticatedUser()).thenReturn(user);

    var response = controller.handle(new HttpRequest<>(new AuthenticatedUserAccountDTORequest()));

    assertEquals(200, response.status());
    assertEquals("account data successfully obtained", response.message());
    assertInstanceOf(AuthenticatedUserAccountDTOResponse.class, response.body());
    assertTrue(response.errors().isEmpty());
    assertTrue(response.success());
  }
}
