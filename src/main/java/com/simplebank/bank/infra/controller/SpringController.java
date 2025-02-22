package com.simplebank.bank.infra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controller.WebController;
import com.simplebank.bank.presentation.controller.port.HttpRequest;
import com.simplebank.bank.usecase.ports.UserDTORequest;
import com.simplebank.bank.usecase.ports.UserDTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class SpringController
{
  public WebController<UserDTOResponse, UserDTORequest> controller;

  public SpringController(WebController<UserDTOResponse, UserDTORequest> controller)
  {
    this.controller = controller;
  }

  @PostMapping
  public ResponseEntity<ObjectNode> createUser(@RequestBody UserDTORequest user)
  {
    var response = controller.handle(new HttpRequest<>(user));

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode body = mapper.createObjectNode();

    body.put("message", response.message());
    body.put("success", response.success());
    body.put("data", mapper.valueToTree(response.body()));

    return ResponseEntity.status(response.status()).body(body);
  }
}
