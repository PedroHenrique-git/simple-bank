package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.domain.Constants;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.RefreshAuthDTORequest;
import com.simplebank.bank.usecases.ports.RefreshAuthDTOResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth/refresh-token")
public class RefreshTokenSpringController extends AbstractSpringController
{
  private final WebController<RefreshAuthDTOResponse, RefreshAuthDTORequest> controller;

  public RefreshTokenSpringController(
      WebController<RefreshAuthDTOResponse, RefreshAuthDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @GetMapping
  public ResponseEntity<ObjectNode> refreshToken(
      @CookieValue(name = Constants.REFRESH_TOKEN_COOKIE_NAME, defaultValue = "")
      String refreshToken)
  {
    var response = controller.handle(new HttpRequest<>(new RefreshAuthDTORequest(refreshToken)));

    var responseBody = response.body();
    var responseErrors = response.errors();

    body.put("message", response.message());
    body.put("success", response.success());
    setBodyData(responseErrors, responseBody);

    return ResponseEntity.status(response.status()).body(body);
  }
}
