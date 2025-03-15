package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.TransferDTORequest;
import com.simplebank.bank.usecases.ports.TransferDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfers")
public class TransferSpringController extends AbstractSpringController
{
  public WebController<TransferDTOResponse, TransferDTORequest> controller;

  public TransferSpringController(
      WebController<TransferDTOResponse, TransferDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @PostMapping
  public ResponseEntity<ObjectNode> transfer(@RequestBody TransferDTORequest transfer)
  {
    var response = controller.handle(new HttpRequest<>(transfer));

    body.put("message", response.message());
    body.put("success", response.success());
    setBodyData(response.errors(), response.body());

    return ResponseEntity.status(response.status()).body(body);
  }
}
