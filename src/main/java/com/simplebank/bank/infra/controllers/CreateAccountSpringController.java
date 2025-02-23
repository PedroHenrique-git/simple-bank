package com.simplebank.bank.infra.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTORequest;
import com.simplebank.bank.usecases.ports.CreateAccountDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class CreateAccountSpringController extends AbstractSpringController
{
  public WebController<CreateAccountDTOResponse, CreateAccountDTORequest> controller;

  public CreateAccountSpringController(
      WebController<CreateAccountDTOResponse, CreateAccountDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @PostMapping
  public ResponseEntity<ObjectNode> createUser(@RequestBody CreateAccountDTORequest user)
  {
    var response = controller.handle(new HttpRequest<>(user));

    body.put("message", response.message());
    body.put("success", response.success());
    body.set("data", mapper.valueToTree(response.body()));

    return ResponseEntity.status(response.status()).body(body);
  }
}
