package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTORequest;
import com.simplebank.bank.usecases.ports.AuthenticatedUserAccountDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/me")
public class AuthenticatedUserAccountController extends AbstractSpringController
{
  private final WebController<AuthenticatedUserAccountDTOResponse, AuthenticatedUserAccountDTORequest>
      controller;

  public AuthenticatedUserAccountController(
      WebController<AuthenticatedUserAccountDTOResponse, AuthenticatedUserAccountDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @GetMapping
  public ResponseEntity<ObjectNode> authenticatedUserAccount()
  {
    var response = controller.handle(new HttpRequest<>(new AuthenticatedUserAccountDTORequest()));

    body.put("message", response.message());
    body.put("success", response.success());
    setBodyData(response.errors(), response.body());

    return ResponseEntity.status(response.status()).body(body);
  }
}
