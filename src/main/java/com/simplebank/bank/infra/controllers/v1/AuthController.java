package com.simplebank.bank.infra.controllers.v1;

import com.simplebank.bank.infra.jpa.entities.UserEntity;
import com.simplebank.bank.presentation.controllers.http.HttpStatus;
import com.simplebank.bank.security.services.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController
{
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;

  public AuthController(AuthenticationManager authenticationManager, JWTService jwtService)
  {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthLoginDTOResponse> login(@RequestBody AuthLoginDTORequest dto)
  {
    var emailPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
    var auth = this.authenticationManager.authenticate(emailPassword);

    var token = jwtService.generateToken((UserEntity) auth.getPrincipal());

    return ResponseEntity.status(HttpStatus.SUCCESS.value()).body(new AuthLoginDTOResponse(token));
  }

  private record AuthLoginDTORequest(String email, String password)
  {
  }

  private record AuthLoginDTOResponse(String token)
  {
  }
}
