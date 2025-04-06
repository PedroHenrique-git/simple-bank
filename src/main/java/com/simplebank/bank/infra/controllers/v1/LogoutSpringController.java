package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.domain.Constants;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthLogoutDTORequest;
import com.simplebank.bank.usecases.ports.AuthLogoutDTOResponse;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/logout")
public class LogoutSpringController extends AbstractSpringController
{
  private final WebController<AuthLogoutDTOResponse, AuthLogoutDTORequest> controller;

  public LogoutSpringController(
      WebController<AuthLogoutDTOResponse, AuthLogoutDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @GetMapping
  public ResponseEntity<ObjectNode> logout(AuthLogoutDTORequest dto)
  {
    var response = controller.handle(new HttpRequest<>(dto));

    var responseBody = response.body();
    var responseErrors = response.errors();

    body.put("message", response.message());
    body.put("success", response.success());
    setBodyData(responseErrors, responseBody);

    HttpCookie cookie = ResponseCookie.from(Constants.REFRESH_TOKEN_COOKIE_NAME)
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .path("/")
        .maxAge(0)
        .build();

    return ResponseEntity.status(response.status())
        .header(HttpHeaders.SET_COOKIE, cookie.toString()).body(body);
  }
}
