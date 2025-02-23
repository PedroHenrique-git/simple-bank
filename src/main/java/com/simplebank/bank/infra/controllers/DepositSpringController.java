package com.simplebank.bank.infra.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.DepositDTORequest;
import com.simplebank.bank.usecases.ports.DepositDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/deposit")
public class DepositSpringController extends AbstractSpringController
{
  public WebController<DepositDTOResponse, DepositDTORequest> controller;

  public DepositSpringController(
      WebController<DepositDTOResponse, DepositDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @PostMapping
  public ResponseEntity<ObjectNode> deposit(@RequestBody DepositDTORequest deposit)
  {
    var response = controller.handle(new HttpRequest<>(deposit));

    body.put("message", response.message());
    body.put("success", response.success());
    body.set("data", mapper.valueToTree(response.body()));

    return ResponseEntity.status(response.status()).body(body);
  }
}
