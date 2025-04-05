package com.simplebank.bank.controllers;

import com.simplebank.bank.mocks.UserMock;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTORequest;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;
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
public class AuthenticatedControllerTest
{
  @Autowired
  public WebController<AuthAuthenticatedUserDTOResponse, AuthAuthenticatedUserDTORequest>
      controller;

  @MockitoBean
  AuthManager authManager;

  @Test
  void testAuthenticatedController()
  {
    when(authManager.getAuthenticatedUser()).thenReturn(UserMock.createClientUser());

    var response = controller.handle(new HttpRequest<>(new AuthAuthenticatedUserDTORequest()));

    assertEquals(200, response.status());
    assertEquals("authenticated user data successfully obtained", response.message());
    assertInstanceOf(AuthAuthenticatedUserDTOResponse.class, response.body());
    assertTrue(response.errors().isEmpty());
    assertTrue(response.success());
  }
}
