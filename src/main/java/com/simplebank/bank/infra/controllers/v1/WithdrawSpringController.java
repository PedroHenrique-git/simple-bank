package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.WithdrawDTORequest;
import com.simplebank.bank.usecases.ports.WithdrawDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts/withdraw")
public class WithdrawSpringController extends AbstractSpringController
{
  private final WebController<WithdrawDTOResponse, WithdrawDTORequest> controller;

  public WithdrawSpringController(
      WebController<WithdrawDTOResponse, WithdrawDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @PostMapping
  public ResponseEntity<ObjectNode> withdraw(@RequestBody WithdrawDTORequest withdraw)
  {
    var response = controller.handle(new HttpRequest<>(withdraw));

    body.put("message", response.message());
    body.put("success", response.success());
    setBodyData(response.errors(), response.body());

    return ResponseEntity.status(response.status()).body(body);
  }
}
