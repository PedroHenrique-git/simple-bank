package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import com.simplebank.bank.usecases.ports.AuthManager;
import java.time.Duration;
import java.util.List;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends AbstractSpringController
{
  private final WebController<AuthLoginDTOResponse, AuthLoginDTORequest> controller;
  private final AuthManager authManager;

  public AuthController(
      WebController<AuthLoginDTOResponse, AuthLoginDTORequest> controller, AuthManager authManager)
  {
    super();

    this.controller = controller;
    this.authManager = authManager;
  }

  @GetMapping("/authenticated-user")
  public ResponseEntity<ObjectNode> authenticatedUser()
  {
    setBodyData(List.of(), authManager.getAuthenticatedUser());

    return ResponseEntity.status(HttpStatus.SUCCESS.value()).body(body);
  }

  @PostMapping("/login")
  public ResponseEntity<ObjectNode> login(@RequestBody AuthLoginDTORequest dto)
  {
    var response = controller.handle(new HttpRequest<>(dto));

    HttpCookie cookie = ResponseCookie.from("session", response.body().refreshToken())
        .httpOnly(true)
        .secure(true)
        .sameSite("Strict")
        .path("/")
        .maxAge(Duration.ofDays(7))
        .build();

    body.put("message", response.message());
    body.put("success", response.success());
    setBodyData(response.errors(), response.body());

    return ResponseEntity.status(response.status())
        .header(HttpHeaders.SET_COOKIE, cookie.toString()).body(body);
  }
}
