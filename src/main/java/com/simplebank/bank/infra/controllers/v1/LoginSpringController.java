package com.simplebank.bank.infra.controllers.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.simplebank.bank.domain.Constants;
import com.simplebank.bank.presentation.controllers.WebController;
import com.simplebank.bank.presentation.controllers.ports.HttpRequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthLoginDTOResponse;
import java.time.Duration;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/login")
public class LoginSpringController extends AbstractSpringController
{
  private final WebController<AuthLoginDTOResponse, AuthLoginDTORequest> controller;

  public LoginSpringController(
      WebController<AuthLoginDTOResponse, AuthLoginDTORequest> controller)
  {
    super();

    this.controller = controller;
  }

  @PostMapping
  public ResponseEntity<ObjectNode> login(@RequestBody AuthLoginDTORequest dto)
  {
    var response = controller.handle(new HttpRequest<>(dto));

    var responseBody = response.body();
    var responseErrors = response.errors();

    body.put("message", response.message());
    body.put("success", response.success());
    setBodyData(responseErrors, responseBody);

    if (!responseErrors.isEmpty() || responseBody == null)
    {
      return ResponseEntity.status(response.status()).body(body);
    }

    HttpCookie cookie =
        ResponseCookie.from(Constants.REFRESH_TOKEN_COOKIE_NAME, responseBody.refreshToken())
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .path("/")
            .maxAge(Duration.ofDays(Constants.REFRESH_TOKEN_COOKIE_EXPIRATION))
            .build();

    return ResponseEntity.status(response.status())
        .header(HttpHeaders.SET_COOKIE, cookie.toString()).body(body);
  }
}
