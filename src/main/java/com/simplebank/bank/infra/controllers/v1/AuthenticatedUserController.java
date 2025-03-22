package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTORequest;
import com.simplebank.bank.usecases.ports.AuthAuthenticatedUserDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/authenticated-user")
public class AuthenticatedUserController extends AbstractSpringController
{
  private final WebController<AuthAuthenticatedUserDTOResponse, AuthAuthenticatedUserDTORequest>
      controller;

  public AuthenticatedUserController(
      WebController<AuthAuthenticatedUserDTOResponse, AuthAuthenticatedUserDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @GetMapping
  public ResponseEntity<ObjectNode> authenticatedUser()
  {
    var response = controller.handle(new HttpRequest<>(new AuthAuthenticatedUserDTORequest()));

    body.put("message", response.message());
    body.put("success", response.success());
    setBodyData(response.errors(), response.body());

    return ResponseEntity.status(response.status()).body(body);
  }
}
