package com.simplebank.bank.security.services;

import com.simplebank.bank.domain.Constants;
import com.simplebank.bank.domain.exceptions.ForbiddenException;
import com.simplebank.bank.domain.exceptions.InvalidCredentialsException;
import com.simplebank.bank.domain.exceptions.InvalidToken;
import com.simplebank.bank.domain.models.User.User;
import com.simplebank.bank.infra.jpa.entities.UserEntity;
import com.simplebank.bank.infra.jpa.mappers.UserEntityMapper;
import com.simplebank.bank.usecases.ports.AuthLoginDTORequest;
import com.simplebank.bank.usecases.ports.AuthManager;
import com.simplebank.bank.usecases.ports.RefreshAuthDTORequest;
import com.simplebank.bank.usecases.ports.RefreshAuthDTOResponse;
import com.simplebank.bank.usecases.ports.TokenDTO;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthManagerInSecurity implements AuthManager
{
  private final AuthenticationManager authenticationManager;
  private final JWTService jwtService;
  private final UserEntityMapper mapper;

  public AuthManagerInSecurity(AuthenticationManager authenticationManager, UserEntityMapper mapper,
                               JWTService jwtService)
  {
    this.authenticationManager = authenticationManager;
    this.mapper = mapper;
    this.jwtService = jwtService;
  }

  @Override
  public TokenDTO authenticate(AuthLoginDTORequest dto) throws InvalidCredentialsException
  {
    try
    {
      var emailPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
      var auth = this.authenticationManager.authenticate(emailPassword);

      var commonToken =
          jwtService.generateToken((UserEntity) auth.getPrincipal(), TokenType.AUTH,
                  Constants.AUTH_TOKEN_EXPIRATION)
              .orElseThrow();
      var refreshToken = jwtService
          .generateToken((UserEntity) auth.getPrincipal(), TokenType.REFRESH,
              Constants.REFRESH_TOKEN_EXPIRATION).orElseThrow();

      return new TokenDTO(commonToken, refreshToken);
    } catch (Exception e)
    {
      log.error("[AUTH MANAGER ERROR]: {}", e.toString());

      throw new InvalidCredentialsException("Invalid email or password");
    }
  }

  @Override
  public boolean isAuthorized(long userId) throws ForbiddenException
  {
    var authenticatedUser = getAuthenticatedUser();

    if (authenticatedUser.getId() == userId)
    {
      return true;
    }

    throw new ForbiddenException();
  }

  @Override
  public User getAuthenticatedUser()
  {
    var userEntity = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return mapper.toModel((UserEntity) userEntity);
  }

  @Override
  public boolean logout()
  {
    SecurityContextHolder.clearContext();

    return true;
  }

  @Override
  public RefreshAuthDTOResponse refreshAuthentication(RefreshAuthDTORequest dto)
      throws InvalidToken, ForbiddenException
  {
    var payload = jwtService.getPayload(dto.refreshToken())
        .orElseThrow(() -> new InvalidToken("Invalid refresh token"));

    if (Objects.equals(payload.type().value(), TokenType.AUTH.value()))
    {
      throw new InvalidToken("Invalid refresh token");
    }

    var userEntity =
        (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (payload.userId() != userEntity.getId())
    {
      throw new ForbiddenException("You can not perform this operation");
    }

    var authToken =
        jwtService.generateToken(userEntity, TokenType.AUTH, 60).orElseThrow();

    return new RefreshAuthDTOResponse(authToken);
  }
}
