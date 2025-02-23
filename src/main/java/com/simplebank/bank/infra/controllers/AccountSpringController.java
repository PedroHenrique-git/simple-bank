package com.simplebank.bank.infra.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AccountDTORequest;
import com.simplebank.bank.usecases.ports.AccountDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountSpringController
{
  public WebController<AccountDTOResponse, AccountDTORequest> controller;

  public AccountSpringController(WebController<AccountDTOResponse, AccountDTORequest> controller)
  {
    this.controller = controller;
  }

  @PostMapping
  public ResponseEntity<ObjectNode> createUser(@RequestBody AccountDTORequest user)
  {
    var response = controller.handle(new HttpRequest<>(user));

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode body = mapper.createObjectNode();

    body.put("message", response.message());
    body.put("success", response.success());
    body.set("data", mapper.valueToTree(response.body()));

    return ResponseEntity.status(response.status()).body(body);
  }
}
